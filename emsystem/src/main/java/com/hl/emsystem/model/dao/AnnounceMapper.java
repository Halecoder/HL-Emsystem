package com.hl.emsystem.model.dao;

import com.hl.emsystem.model.pojo.Announce;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnounceMapper {
    int deleteByPrimaryKey(String title);

    int insert(Announce record);

    int insertSelective(Announce record);

    Announce selectByPrimaryKey(String title);

    int updateByPrimaryKeySelective(Announce record);

    int updateByPrimaryKey(Announce record);

    List<Announce> selectAllNotice();


}