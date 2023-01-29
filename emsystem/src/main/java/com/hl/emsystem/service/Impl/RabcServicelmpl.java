package com.hl.emsystem.service.Impl;

import com.hl.emsystem.model.dao.RabcMapper;
import com.hl.emsystem.model.pojo.Node;
import com.hl.emsystem.service.RabcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RabcServicelmpl implements RabcService {
    @Autowired
    RabcMapper rabcMapper;
    @Override
    public List<Node> selectNodeByUserId(Long userId) {

        return rabcMapper.selectNodeByUserId(userId);
    }

    @Override
    public List<Long> selectTreeIdListByRoleId(Long roleId) {
        return rabcMapper.selectTreeIdListByRoleId(roleId);
    }
}

