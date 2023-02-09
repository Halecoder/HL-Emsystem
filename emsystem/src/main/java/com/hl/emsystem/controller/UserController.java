package com.hl.emsystem.controller;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.pagehelper.PageInfo;
import com.google.code.kaptcha.Producer;
import com.hl.emsystem.common.ApiRestResponse;
import com.hl.emsystem.common.Constant;
import com.hl.emsystem.common.UserContext;
import com.hl.emsystem.exception.EmSystemExceptionEnum;
import com.hl.emsystem.model.pojo.*;
import com.hl.emsystem.model.vo.UserExcelVo;
import com.hl.emsystem.service.Impl.DepartmentServiceImpl;
import com.hl.emsystem.service.PeopleService;
import com.hl.emsystem.service.RabcService;
import com.hl.emsystem.service.UserService;
import com.hl.emsystem.utils.BeanCopyUtils;
import com.hl.emsystem.utils.excel.ExcelImportListener;
import com.hl.emsystem.utils.excel.ExcelResult;
import com.hl.emsystem.utils.excel.ExcelUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 * 用户管理
 */
@CrossOrigin

@RestController
@RequestMapping("/api")
@Tag(name = "用户信息管理")
public class UserController {

    private Long currentUserId;

    @Autowired
    private UserService userService;
    @Autowired
    private RabcService rabcService;

    @Autowired
    private PeopleService peopleService;

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Resource
    private Producer kaptchaProducer;


    /**
     * 验证码生成
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/verify_code")
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //响应立即过期
        response.setDateHeader("Expires", 0);
        //不缓存任何图片数据
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.setHeader("Cache-Control", "post-check=0,pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/png");
        //生成验证码图片
        String text = kaptchaProducer.createText();
        request.getSession().setAttribute("kaptchaVerifyCode", text);
        BufferedImage image = kaptchaProducer.createImage(text);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * @param map
     * @param request
     * @return 获得Token数据
     * @throws ServletException
     * @throws IOException
     */
    @PostMapping("/user/login")
    @ResponseBody
    public ApiRestResponse login(@RequestBody Map<String, String> map, HttpServletRequest request) throws ServletException, IOException {

        String verifyCode = (String) request.getSession().getAttribute("kaptchaVerifyCode");
        String verificationCode = (String) map.get("verificationCode");
        String username = (String) map.get("username");
        String password = (String) map.get("password");

        if (verificationCode == null || verifyCode == null || !verificationCode.equalsIgnoreCase(verifyCode)) {
            return ApiRestResponse.error(EmSystemExceptionEnum.VERIFICATION_CODE_ERROR);
        }
        if (password.length() < 8) {
            return ApiRestResponse.error(EmSystemExceptionEnum.PASSWORD_TOO_SHORT);
        }
        User user = userService.login(username, password);
        user.setPassword(null);
//        获取当前userId
        currentUserId = user.getUserId();
        Algorithm algorithm = Algorithm.HMAC256(Constant.JWT_KEY);
        String token = JWT.create()
                .withClaim(Constant.NAME, user.getName())
                .withClaim(Constant.USER_NAME, user.getUsername())
                .withClaim(Constant.USER_ID, user.getUserId())
                .withClaim(Constant.PEOPLE_ID, user.getPeopleId())
                //过期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + Constant.EXPIRE_TIME))
                .sign(algorithm);

        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put(Constant.TOKEN, token);
        return ApiRestResponse.success(responseData);
    }

    /**
     * @param token 令牌
     * @return 用户信息
     */
    @GetMapping("/user/user_info")
    @ResponseBody
    public ApiRestResponse userInfo(@RequestParam("token") String token) {
        Long userId = JWT.decode(token).getClaim(Constant.USER_ID).asLong();
        Long peopleId = JWT.decode(token).getClaim(Constant.PEOPLE_ID).asLong();
        String username = JWT.decode(token).getClaim(Constant.USER_NAME).asString();
        Map ResponseList = null;
        ResponseList = new LinkedHashMap();
        People people = peopleService.selectByPrimaryKey(peopleId);
        Department department = departmentService.selectByPrimaryKey(people.getDepartmentId());
        ResponseList.put("people", people);
        ResponseList.put("department", department);
        ResponseList.put("username", username);
        return ApiRestResponse.success(ResponseList);
    }

    /**
     * @param token 令牌
     * @return 获取不同用户对应功能
     */
    @GetMapping("/user/user_router")
    @ResponseBody
    public ApiRestResponse getSidebar(@RequestParam("token") String token) {
        Long userId = JWT.decode(token).getClaim(Constant.USER_ID).asLong();
        List<Node> nodes = rabcService.selectNodeByUserId(userId);

        return ApiRestResponse.success(nodes);
    }


    /**
     * 用户登出
     *
     * @return
     */
    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout() {
        // ThreadLocal值清除，防止内存泄漏
        UserContext.remove();
        return ApiRestResponse.success();
    }


    /**
     * 查询用户列表
     *
     * @param user
     * @param pageQuery
     * @return
     */
    @GetMapping("/system/user/list")
    public ApiRestResponse listUser(User user, PageQuery pageQuery) {
        PageInfo<User> users = userService.selectlistUser(user, pageQuery);
        return ApiRestResponse.success(users);
    }


    /**
     * 查询用户详细信息
     *
     * @param userId
     * @return
     */
    @GetMapping(value = {"/system/user/", "/system/user/{userId}"})
    public ApiRestResponse getUser(@PathVariable(value = "userId", required = false) Long userId) {
        Map<String, Object> response = new HashMap<>();
        List<Role> roles = userService.selectAllRole();
        response.put("roles", roles);
        if (userId != null) {
            User user = userService.selectByUserId(userId);
            response.put("user", user);
            response.put("roleIds", userService.selectAllRoleIdByUserId(userId));
        }
        return ApiRestResponse.success(response);
    }


    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @PostMapping("/system/user/add")
    public ApiRestResponse addUser(@RequestBody User user) {
        if (userService.checkUserNameHave(user)) {
            return ApiRestResponse.error(EmSystemExceptionEnum.USERNAME_HAVE);
        }
        userService.addUserInfo(user);
        return ApiRestResponse.success();
    }

////    删除用户
//    @DeleteMapping("/system/user/{userId}")
//    public ApiRestResponse deleteUser(@PathVariable Long userId){
//        userService.deleteUserInfo(userId);
//        return ApiRestResponse.success();
//    }


    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @PutMapping("/system/user")
    public ApiRestResponse updateUserInfo(@RequestBody User user) {
        userService.updateUserInfo(user);
        return ApiRestResponse.success();
    }

    /**
     * 重置用户密码
     *
     * @param user
     * @return
     */
    @PutMapping("/system/user/resetPwd")
    public ApiRestResponse resetPassword(@RequestBody User user) {
        userService.resetPwd(user, user.getPassword());
        return ApiRestResponse.success();
    }

    /**
     * 下载excel模板
     *
     * @param response
     */
    @PostMapping("/system/user/importTemplate")
    @ResponseBody
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil.exportExcel(new ArrayList<>(), "用户数据", UserExcelVo.class, response);
    }


    /**
     * 导入Excel用户信息
     *
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @PostMapping("/system/user/importData")
    @ResponseBody
    public ApiRestResponse importStudent(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelResult<UserExcelVo> result = ExcelUtil.importExcel(file.getInputStream(), UserExcelVo.class, new ExcelImportListener(updateSupport));
        return ApiRestResponse.success(result.getAnalysis());
    }


    /**
     * excel导出用户信息
     *
     * @param user
     * @param response
     */
    @PostMapping("/system/user/export")
    public void export(User user, HttpServletResponse response) {
        List<User> userList = userService.exportAllUser(user);
        for (User userRole : userList) {
            List<Long> roleIds = userService.selectAllRoleIdByUserId(userRole.getUserId());
            userRole.setRoleIds(Convert.toLongArray(roleIds));
        }
        List<UserExcelVo> userVo = BeanCopyUtils.copyList(userList, UserExcelVo.class);
        ExcelUtil.exportExcel(userVo, "用户数据", UserExcelVo.class, response);

    }


    //    批量删除用户
    @DeleteMapping("/system/user/{userIds}")
    public ApiRestResponse deleteUsers(@PathVariable Long[] userIds) {
        if (ArrayUtil.contains(userIds, currentUserId)) {
            return ApiRestResponse.error(EmSystemExceptionEnum.NOT_DELETE);
        }
        userService.deleteAllUserInfo(userIds);
        return ApiRestResponse.success();
    }


}

