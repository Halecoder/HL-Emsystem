package com.hl.emsystem.model.dao;

import com.hl.emsystem.model.pojo.Recruit;

public interface RecruitMapper {
    int deleteByPrimaryKey(String title);

    int insert(Recruit record);

    int insertSelective(Recruit record);

    Recruit selectByPrimaryKey(String title);

    int updateByPrimaryKeySelective(Recruit record);

    int updateByPrimaryKey(Recruit record);
}