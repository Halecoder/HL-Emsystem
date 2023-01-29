package com.hl.emsystem.model.dao;

import com.hl.emsystem.model.pojo.SysRoleUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleUserMapper extends BaseMapperPlus<SysRoleUserMapper,SysRoleUser,SysRoleUser>{
    int deleteByPrimaryKey(Long ruId);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    SysRoleUser selectByPrimaryKey(Long ruId);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);

    List<Long> selectRoleIdByUserId(Long userId);
}