package com.hl.emsystem.model.dao;

import com.hl.emsystem.model.pojo.GraStudent;
import org.springframework.stereotype.Repository;

@Repository
public interface GraStudentMapper {
    int deleteByPrimaryKey(Integer stuno);

    int insert(GraStudent record);

    int insertSelective(GraStudent record);

    GraStudent selectByPrimaryKey(String stuno);

    int updateByPrimaryKeySelective(GraStudent record);

    int updateByPrimaryKey(GraStudent record);
}