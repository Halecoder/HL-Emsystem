package com.hl.emsystem.controller.admin;

import com.github.pagehelper.PageInfo;
import com.hl.emsystem.common.ApiRestResponse;
import com.hl.emsystem.exception.EmSystemException;
import com.hl.emsystem.exception.EmSystemExceptionEnum;
import com.hl.emsystem.model.pojo.*;
import com.hl.emsystem.service.MenuService;
import com.hl.emsystem.service.RabcService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员操作管理
 * @author 行者无疆
 */
@RestController
@RequestMapping("/api")
@Tag(name = "管理员操作管理")
public class AdminController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RabcService rabcService;

    /**
     * 获取树形数据
     * @return
     */
    @GetMapping("/demo/tree/list")
    @Operation(summary = "This method is used to get the treeList.")
    public ApiRestResponse treeList (){
        List<Node> nodes = menuService.list();
        return ApiRestResponse.success(nodes);
    }

    /**
     * 菜单添加
     * @param node
     * @return
     */
    @PostMapping("/demo/tree/add")
    public ApiRestResponse treeAdd (@RequestBody Node node){
        menuService.addMenu(node);
        return ApiRestResponse.success();
    }

    /**
     * 查询树型菜单详细
     * @param nodeId
     * @return
     */
    @GetMapping("/demo/tree/{nodeId}")
    public ApiRestResponse queryRoleNode(@NotNull(message = "主键不能为空")
                                             @PathVariable("nodeId") Long nodeId){

        return ApiRestResponse.success( menuService.quaryNode(nodeId));

    }

    /**
     * 修改树形菜单数据
     * @param node
     * @return
     */
//    修改树节点
    @PutMapping("/demo/tree")
    public ApiRestResponse updateTreeNode(@RequestBody Node node){

        menuService.updateNode(node);
        return ApiRestResponse.success();
    }

    /**
     * 删除树形菜单数据
     * @param nodeId
     * @return
     * @throws EmSystemException
     */
//    删除树节点
    @DeleteMapping("/demo/tree/{nodeId}")
 public ApiRestResponse deleteTreeNode(@NotNull(message = "主键不能为空")
                                           @PathVariable("nodeId") Long nodeId) throws EmSystemException {
        menuService.deleteNode(nodeId);
        return  ApiRestResponse.success();
    }






//    查询角色
    @GetMapping("/demo/role/list")
    public ApiRestResponse roleList (Role role, PageQuery pageQuery){

        PageInfo<Role> pageInfo = menuService.selectRoleList(role,pageQuery);
        return ApiRestResponse.success(pageInfo);
    }
// 查询角色详细
    @GetMapping("/demo/role/{roleId}")
    public ApiRestResponse queryTreeNode(@NotNull(message = "主键不能为空")
                                     @PathVariable("roleId") Long roleId){

    return ApiRestResponse.success( menuService.quaryRole(roleId));

    }

    @GetMapping("/demo/tree/treeselect")
    public ApiRestResponse treeselect(Node menu) {
        List<Node> menus = menuService.selectMenuList(menu);
        return ApiRestResponse.success(menus);
    }

// 根据角色ID查询菜单下拉树结构
    @GetMapping("/demo/tree/roleTreeSelect/{roleId}")
    public ApiRestResponse treeSelected(@NotNull(message = "主键不能为空")
                                            @PathVariable("roleId") Long roleId){
        List<Node> menus = menuService.list();
        Map<String, Object> ajax = new HashMap<>();
        ajax.put("checkedKeys", rabcService.selectTreeIdListByRoleId(roleId));
        ajax.put("menus",menus);
        return ApiRestResponse.success(ajax);
    }

  // 修改角色
    @PutMapping("/demo/role")
    public ApiRestResponse updateRole(@Validated @RequestBody Role role) {

        if (menuService.updateRole(role) > 0) {
            return ApiRestResponse.success();
        }
        return ApiRestResponse.error(EmSystemExceptionEnum.UPDATEROLE_FAIL);
    }

//    新增角色按钮
    @PostMapping("/demo/role/add")
    public ApiRestResponse addRole(@Validated @RequestBody Role role){
            menuService.addRole(role);
        return ApiRestResponse.success();
    }

//    删除角色
    @DeleteMapping("/demo/role/{roleId}")
    public ApiRestResponse deleteRole(@NotNull(message = "主键不能为空")
                                          @PathVariable("roleId") Long roleId){
        menuService.deleteRole(roleId);
        return ApiRestResponse.success();
    }

    // 查询角色已授权用户列表
    @GetMapping("/demo/role/authUser/allocatedList")
    public ApiRestResponse allocatedUserList ( User user ,PageQuery pageQuery){
        PageInfo <User> users =  menuService.selectAllocatedUserList(user,pageQuery);
    return ApiRestResponse.success(users);

    }

//    查询未授权用户
    @GetMapping("/demo/role/authUser/unallocatedList")
    public ApiRestResponse unallocatedList (User user ,PageQuery pageQuery){
        PageInfo <User> users = menuService.selectUnallocatedList(user,pageQuery);
        return ApiRestResponse.success(users);
    }

//    取消已经授权的用户
    @PutMapping("/demo/role/authUser/cancel")
    public ApiRestResponse authUserCancel(@RequestBody SysRoleUser roleUser){
        if(menuService.authUserCancel(roleUser)>0) {
            return ApiRestResponse.success();
        }
        return ApiRestResponse.error(EmSystemExceptionEnum.CANCEL_AUTHUSER_FAIL);
    }

//    选择授权用户
    @PutMapping("/demo/role/authUser/selectAll")
    public ApiRestResponse authUserSelectAll(Long roleId, Long[] userIds){
        int rows =  menuService.authUserSelect(roleId,userIds);
        return  ApiRestResponse.success();
    }

//    批量取消用户授权
    @PutMapping("/demo/role/authUser/cancelAll")
    public  ApiRestResponse authUserCancelAll(Long roleId, Long[] userIds){
        menuService.cancelAllAuthUser(roleId, userIds);
        return ApiRestResponse.success();
    }



}
