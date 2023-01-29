package com.hl.emsystem.model.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hl.emsystem.model.pojo.Node;
import com.hl.emsystem.model.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RabcMapper extends BaseMapper  {
    List<Node> selectNodeByUserId(Long userId);

    List<Long> selectTreeIdListByRoleId(Long roleId);

    List<Node> selectMenuListByUserId();

    List <User> selectUserAllocatedByRoleId(Long roleId);

}
