package com.hl.emsystem.model.dao;

import com.hl.emsystem.model.pojo.Booth;

public interface BoothMapper {
    int insert(Booth record);

    int insertSelective(Booth record);
}