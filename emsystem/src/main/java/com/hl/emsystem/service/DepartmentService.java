package com.hl.emsystem.service;

import com.hl.emsystem.model.pojo.Department;

public interface DepartmentService {

   Department selectByPrimaryKey(Long departmentId);
}
