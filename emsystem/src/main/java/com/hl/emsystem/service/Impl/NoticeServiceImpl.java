package com.hl.emsystem.service.Impl;

import com.hl.emsystem.exception.EmSystemException;
import com.hl.emsystem.exception.EmSystemExceptionEnum;
import com.hl.emsystem.model.dao.AnnounceMapper;
import com.hl.emsystem.model.pojo.Announce;
import com.hl.emsystem.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    AnnounceMapper announceMapper;

    @Override
    public Announce addNotice(Announce announce) throws EmSystemException {
        int count = announceMapper.insertSelective(announce);
        if (count == 0) {
            throw new EmSystemException(EmSystemExceptionEnum.INSERT_ERROR);
        }
        return announce;
    }

    @Override
    public List<Announce> selectAllNotice() {
        return announceMapper.selectAllNotice();
    }

    @Override
    public int deleteNotice(String title) {
        return announceMapper. deleteByPrimaryKey(title);
    }
}

