package com.hl.emsystem.controller.stu;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.hl.emsystem.model.pojo.ResumeWithBLOBs;
import com.hl.emsystem.model.vo.Json.MyJsonResume;
import com.hl.emsystem.service.StuService;
import com.hl.emsystem.utils.BeanCopyUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class StuControllerTest {

    @Autowired
    private StuService stuService;
    @Test
    @DisplayName("测试获取简历json中数据")
    void getMyJsonResume() {
        ResumeWithBLOBs resume =  stuService.getResInfo("admin");
        JSONArray edu = resume.getEducation();
       for(int i = 0;i < edu.size();i++){

           JSONObject jsonObject = edu.getJSONObject(i);
           String Type = jsonObject.getStr("degree");
           System.out.println(Type);
       }
    }

    @Test
    @DisplayName("测试拷贝到MyJsonResume")
    void copyToMyJsonResume(){
        ResumeWithBLOBs resume =  stuService.getResInfo("admin");
        MyJsonResume myJsonResume = new MyJsonResume();
        BeanCopyUtils.copy(resume,myJsonResume);

    }

    @Test
    @DisplayName("测试Bean拷贝到map")
    void copyBeanToMap(){
        ResumeWithBLOBs resume =  stuService.getResInfo("admin");
        MyJsonResume myJsonResume = new MyJsonResume();

        Map<String,Object> contact = new HashMap<>();
        List<String> beans = new ArrayList<>();
        beans.add(resume.getMail());
        beans.add(String.valueOf(resume.getPhone()));
        beans.add(resume.getCity());
        beans.add(resume.getGithub());
        beans.add(resume.getAddress());
        beans.add(resume.getWebsite());

        System.out.println(BeanUtil.beanToMap(resume,contact,false,false));

    }

}