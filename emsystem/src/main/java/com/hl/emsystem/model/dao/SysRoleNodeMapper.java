package com.hl.emsystem.model.dao;

import com.hl.emsystem.model.pojo.SysRoleNode;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleNodeMapper extends BaseMapperPlus<SysRoleNodeMapper, SysRoleNode, SysRoleNode>{
    int deleteByPrimaryKey(Long rnId);

    int insert(SysRoleNode record);

    int insertSelective(SysRoleNode record);

    SysRoleNode selectByPrimaryKey(Long rnId);

    int updateByPrimaryKeySelective(SysRoleNode record);

    int updateByPrimaryKey(SysRoleNode record);
}