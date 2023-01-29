package com.hl.emsystem.service;

import com.hl.emsystem.exception.EmSystemException;
import com.hl.emsystem.model.pojo.Announce;

import java.util.List;

public interface NoticeService {

    Announce addNotice(Announce announce) throws EmSystemException;

    List<Announce> selectAllNotice();


    int deleteNotice(String title);
}
