package com.hl.emsystem.model.dao;

import com.hl.emsystem.model.pojo.People;
import org.springframework.stereotype.Repository;

@Repository("PeopleMapper")
public interface PeopleMapper extends BaseMapperPlus<PeopleMapper, People,People>{
    int deleteByPrimaryKey(Long peopleId);

    int insert(People record);

    int insertSelective(People record);

    People selectByPrimaryKey(Long peopleId);

    int updateByPrimaryKeySelective(People record);

    int updateByPrimaryKey(People record);
}