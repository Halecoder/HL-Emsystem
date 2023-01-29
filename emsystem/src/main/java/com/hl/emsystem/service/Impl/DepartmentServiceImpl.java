package com.hl.emsystem.service.Impl;

import com.hl.emsystem.model.dao.DepartmentMapper;
import com.hl.emsystem.model.pojo.Department;
import com.hl.emsystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public Department selectByPrimaryKey(Long departmentId) {
        return departmentMapper.selectByPrimaryKey(departmentId);
    }
}

