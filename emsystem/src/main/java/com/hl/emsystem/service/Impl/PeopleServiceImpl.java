package com.hl.emsystem.service.Impl;

import com.hl.emsystem.model.dao.PeopleMapper;
import com.hl.emsystem.model.pojo.People;
import com.hl.emsystem.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeopleServiceImpl implements PeopleService {
    @Autowired
    PeopleMapper peopleMapper;

    @Override
    public People selectByPrimaryKey(Long peopleId) {
        return peopleMapper.selectByPrimaryKey(peopleId);
    }
}

