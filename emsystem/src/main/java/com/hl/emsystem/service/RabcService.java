package com.hl.emsystem.service;

import com.hl.emsystem.model.pojo.Node;

import java.util.List;

public interface RabcService {
    /**
     * 根据用户id查询用户的权限
     * @param userId
     * @return
     */
    List<Node> selectNodeByUserId(Long userId);

    List<Long> selectTreeIdListByRoleId(Long roleId);
}

