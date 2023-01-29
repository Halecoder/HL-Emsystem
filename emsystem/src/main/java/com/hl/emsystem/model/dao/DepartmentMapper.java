package com.hl.emsystem.model.dao;

import com.hl.emsystem.model.pojo.Department;
import org.springframework.stereotype.Repository;

@Repository("DepartmentMapper")
public interface DepartmentMapper extends BaseMapperPlus<DepartmentMapper, Department,Department>{
    int deleteByPrimaryKey(Long departmentId);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Long departmentId);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
}