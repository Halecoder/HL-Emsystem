package com.hl.emsystem.model.dao;

import com.hl.emsystem.model.pojo.Node;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeMapper extends BaseMapperPlus<NodeMapper, Node, Node>{
    int deleteByPrimaryKey(Long nodeId);

    int insert(Node record);

    int insertSelective(Node record);

    Node selectByPrimaryKey(Long nodeId);

    int updateByPrimaryKeySelective(Node record);

    int updateByPrimaryKey(Node record);

    List<Node> selectAllList();


}