package com.hl.emsystem.controller.stu;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.hl.emsystem.common.ApiRestResponse;
import com.hl.emsystem.model.pojo.GraStudent;
import com.hl.emsystem.model.pojo.Resume;
import com.hl.emsystem.model.pojo.ResumeWithBLOBs;
import com.hl.emsystem.model.vo.Json.MyJsonResume;
import com.hl.emsystem.service.StuService;
import com.hl.emsystem.service.UserService;
import com.hl.emsystem.utils.DownloadResume;
import com.ruiyun.jvppeteer.core.browser.Browser;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 学生信息管理
 */
@RestController
@Validated
@RequestMapping("/api")
@Tag(name = "学生信息管理")
public class StuController {

    @Autowired
    private StuService stuService;

    @Autowired
    private UserService userService;
    /**
     * 添加学生信息
     * @param stuInfo
     * @return
     */
    @PostMapping("/stu/upStuInfo")
    public ApiRestResponse addStuInfo( @RequestBody GraStudent stuInfo) {
        stuService.upStuInfo(stuInfo);
        return ApiRestResponse.success();
    }

    /**
     * 查询学生详细
     * @param stuno
     * @return
     */
    @GetMapping("/stu/getStuInfo/{stuno}")
    public ApiRestResponse getStuInfo(@PathVariable(value = "stuno") String stuno){
        if(stuService.getInfo(stuno)== null){
            return ApiRestResponse.success();
        }
       return ApiRestResponse.success( stuService.getInfo(stuno));
    }

    /**
     * 更新学生信息
     * @param stuInfo
     * @return
     */
    @PutMapping("/stu/updateStuInfo")
    public ApiRestResponse upStuInfo(@RequestBody GraStudent stuInfo){
        stuService.updateStuInfo(stuInfo);
        return ApiRestResponse.success();
    }


    /**
     * 上传学生简历
     * @param resume
     * @return
     */
    @PostMapping("/stu/upResume")
    public ApiRestResponse addResume(@RequestBody ResumeWithBLOBs resume){
        stuService.upResInfo(resume);
        return ApiRestResponse.success();
    }

    /**
     * 查询简历详细
     * @param stuno
     * @return
     */
    @GetMapping("/stu/getResume/{stuno}")
    public ApiRestResponse getResume(@PathVariable(value = "stuno") String stuno){
        if(stuService.getResInfo(stuno)== null){
            return ApiRestResponse.success();
        }
        return ApiRestResponse.success( stuService.getResInfo(stuno));
    }

    /**
     * 更新简历信息
     * @param resInfo
     * @return
     */
    @PutMapping("/stu/updateResume")
    public ApiRestResponse updateResume(@RequestBody ResumeWithBLOBs resInfo){
        stuService.updateResInfo(resInfo);
        return ApiRestResponse.success();
    }


    /**
     * 获取自定义简历的Json数据格式
     * @param stuno
     * @return
     */
    @GetMapping("/stu/getMyJsonResume/{stuno}")
    public ApiRestResponse getMyJsonResume(@PathVariable(value = "stuno") String stuno){
//        获取Resume表里所有数据
        Resume resume =  stuService.getResInfo(stuno);
        GraStudent graStudent = stuService.getInfo(stuno);

        MyJsonResume myJsonResume = new MyJsonResume();
        BeanUtil.copyProperties(resume,myJsonResume, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        Map<String,Object> Contact = new HashMap<>();
        Contact.put("email",resume.getMail());
        Contact.put("phone",resume.getPhone());
        Contact.put("street",resume.getAddress());
        Contact.put("city",resume.getCity());
        Contact.put("website",resume.getWebsite());
        Contact.put("email",resume.getGithub());

        Map<String,Object> birth = new HashMap<>();
        birth.put("year",graStudent.getBirthdate());
        birth.put("location",resume.getCity());
        myJsonResume.setBirth(birth);
        myJsonResume.setContact(Contact);
        return ApiRestResponse.success(myJsonResume);
    }


    /**
     * 批量生成图片
     * @param resumeNames
     * @throws Exception
     */
    @PostMapping("/stu/download/resumeImages")
    public void downloadResumeImages (@RequestBody  String[] resumeNames, HttpServletRequest request) throws Exception {
        DownloadResume downloadResume = new DownloadResume();
        //        首先token登陆
        Browser browser = downloadResume.autoLogin(request);
        String fileName = downloadResume.getStuName(request);
        downloadResume.DownloadImages(resumeNames,browser,fileName);
    }

    /**
     * 下载pdf或者图片
     * @param resumeName
     * @throws Exception
     */
    @PostMapping("/stu/download/resume/{Name}")
    public void  downloadResumePdf(@PathVariable(value = "Name")	String resumeName, @RequestParam String Type, HttpServletRequest request, HttpServletResponse response)throws Exception{

        DownloadResume downloadResume = new DownloadResume();
        String fileName = downloadResume.getStuName(request);
        if(Type.equals("pdf")) {
            //        首先token登陆
            Browser browser = downloadResume.autoLogin(request);
            downloadResume.DownloadPdf(resumeName,browser,fileName);
        }

//        本地文件读取为输出流转给前端
        downloadResume.fileExportStream(resumeName,fileName,response,Type);

    }




}
