package com.hl.emsystem.model.dao;

import com.hl.emsystem.model.pojo.Resume;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeMapper {
    int deleteByPrimaryKey(String stuno);

    int insert(Resume record);

    int insertSelective(Resume record);

    Resume selectByPrimaryKey(String stuno);

    int updateByPrimaryKeySelective(Resume record);

    int updateByPrimaryKey(Resume record);
}