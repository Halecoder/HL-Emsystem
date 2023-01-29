package com.hl.emsystem.controller;


import com.hl.emsystem.common.ApiRestResponse;
import com.hl.emsystem.exception.EmSystemExceptionEnum;
import com.hl.emsystem.model.pojo.Announce;
import com.hl.emsystem.service.NoticeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 通知公告管理
 */
@RestController
@RequestMapping("/api")
@Tag(name = "通知公告管理")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;


    @PostMapping("/notice/publish")
    public ApiRestResponse publishNotice(@RequestParam("title") String title, @RequestParam("type") String type
            , @RequestParam("people") String people
            , @RequestParam("content") String content) {

        Announce announce = new Announce();
        announce.setTitle(title);
        announce.setType(type);
        announce.setTime(new Date());
        announce.setPeople(people);
        announce.setContent(content);

        if (noticeService.addNotice(announce) != null) {
            return ApiRestResponse.success();
        }else
            return ApiRestResponse.error(EmSystemExceptionEnum.INSERT_ERROR);
    }

    @GetMapping("/notice/selectAll")
    public ApiRestResponse selectAllNotice(){
        List<Announce> announceList = noticeService.selectAllNotice();
        return ApiRestResponse.success(announceList);
    }


    @GetMapping("/notice/deleteNotice")
    public ApiRestResponse deleteNotice(@RequestParam("title") String title){
        int count = noticeService.deleteNotice(title);
        if (count == 0) {
            return ApiRestResponse.error(EmSystemExceptionEnum.DELETE_ERROR);
        }
        return ApiRestResponse.success();
    }


}
