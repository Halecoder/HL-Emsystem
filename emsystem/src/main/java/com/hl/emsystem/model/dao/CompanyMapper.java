package com.hl.emsystem.model.dao;

import com.hl.emsystem.model.pojo.Company;

public interface CompanyMapper {
    int deleteByPrimaryKey(String firmname);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(String firmname);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);
}