package com.hl.emsystem.model.dao;

import com.hl.emsystem.model.pojo.Resume;
import com.hl.emsystem.model.pojo.ResumeWithBLOBs;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeMapper extends BaseMapperPlus<ResumeMapper, ResumeWithBLOBs, ResumeWithBLOBs> {
    int deleteByPrimaryKey(String stuno);

    int insert(ResumeWithBLOBs record);

    int insertSelective(ResumeWithBLOBs record);

    ResumeWithBLOBs selectByPrimaryKey(String stuno);

    int updateByPrimaryKeySelective(ResumeWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ResumeWithBLOBs record);

    int updateByPrimaryKey(Resume record);
}