package com.hl.emsystem.controller.stu;

import com.hl.emsystem.common.ApiRestResponse;
import com.hl.emsystem.model.pojo.GraStudent;
import com.hl.emsystem.model.pojo.ResumeWithBLOBs;
import com.hl.emsystem.service.StuService;
import com.hl.emsystem.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/stu/updateResume")
    public ApiRestResponse updateResume(@RequestBody ResumeWithBLOBs resInfo){
        stuService.updateResInfo(resInfo);
        return ApiRestResponse.success();
    }



}
