package com.hl.emsystem.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hl.emsystem.exception.EmSystemException;
import com.hl.emsystem.exception.EmSystemExceptionEnum;
import com.hl.emsystem.model.dao.*;
import com.hl.emsystem.model.pojo.*;
import com.hl.emsystem.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RabcMapper rabcMapper;

    @Autowired
    SysRoleNodeMapper sysRoleNodeMapper;

    @Autowired
    SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<Node> list() {
     List<Node> nodeList = nodeMapper.selectAllList();
        return nodeList;
    }

    @Override
    public void addMenu(Node node) throws EmSystemException{
//        if(node.getParentId() == 0){
//            throw new EmSystemException(EmSystemExceptionEnum.NEED_MULU);
//        }
        nodeMapper.insertSelective(node);
    }

    @Override
    public Node quaryNode(long nodeId){
      return  nodeMapper.selectByPrimaryKey(nodeId);
    }

    @Override
    public PageInfo<Role> selectRoleList(Role role, PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        List<Role> roleList = roleMapper.selectAll();
        PageInfo pageInfo = new PageInfo<>(roleList);
        return pageInfo;
    }

    @Override
    public void updateNode(Node node) {
        boolean isFalse = nodeMapper.exists(new LambdaQueryWrapper<Node>().eq(Node::getParentId, node.getNodeId()))&&node.getUrl()!=null;
        if(isFalse){
            throw new EmSystemException(EmSystemExceptionEnum.UPDATE_NODE_ERROR);
        }

        nodeMapper.updateByPrimaryKey(node);
    }

    @Override
    public void deleteNode(Long nodeId)  {
        boolean isFalse = nodeMapper.exists(new LambdaQueryWrapper<Node>().eq(Node::getParentId, nodeId));
        if(isFalse){
            throw new EmSystemException(EmSystemExceptionEnum.DELETE_NODE_ERROR);
        }
        nodeMapper.deleteByPrimaryKey(nodeId);
    }

    @Override
    public Role quaryRole(Long roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public List<Node> selectMenuList(Node menu) {
        return rabcMapper.selectMenuListByUserId();
    }

    @Override
    public int updateRole(Role role) {
        //修改角色信息
        roleMapper.updateByPrimaryKey(role);
        //删除用户的菜单权限

        sysRoleNodeMapper.delete(new LambdaQueryWrapper<SysRoleNode>().eq(SysRoleNode::getRoleId, role.getRoleId()));

        return insertRoleMenu(role);
    }

//    添加角色
    @Override
    public void addRole(Role role) {
        //先插入用户信息
        roleMapper.insertSelective(role);
        insertRoleMenu(role);


    }

    @Override
    public void deleteRole(Long roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public PageInfo<User> selectAllocatedUserList(User user, PageQuery pageQuery) {
        List <com.hl.emsystem.model.pojo.User> userList = rabcMapper.selectUserAllocatedByRoleId(user.getRoleId());
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        PageInfo pageInfo = new PageInfo<>(userList);
        pageInfo.setList(userList);
        return pageInfo;
    }

    /**
     * 取消授权用户角色
     *
     * @param roleUser 用户和角色关联信息
     * @return 结果
     */
    @Override
    public int authUserCancel(SysRoleUser roleUser) {
            return sysRoleUserMapper.delete(new LambdaQueryWrapper<SysRoleUser>()
                    .eq(SysRoleUser::getRoleId,roleUser.getRoleId())
                    .eq(SysRoleUser::getUserId, roleUser.getUserId()));

    }

    @Override
    public PageInfo<User> selectUnallocatedList(User user, PageQuery pageQuery) {
        List <User> userAllocatedList = rabcMapper.selectUserAllocatedByRoleId(user.getRoleId());
        List <User> userList = userMapper.selectAllUser();
        List <User> userAllNocatedList =userList.stream().filter(add -> userAllocatedList.stream()
                .noneMatch(all -> Objects.equals(add.getUserId(), all.getUserId()))).collect(Collectors.toList());
                        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        PageInfo pageInfo = new PageInfo<>(userAllNocatedList);
        pageInfo.setList(userAllNocatedList);
        return pageInfo;
    }

    @Override
    public int authUserSelect(Long roleId, Long[] userIds) {
        int rows = 1;
        List<SysRoleUser> list = new ArrayList<SysRoleUser>();
        for (Long userId : userIds) {
            SysRoleUser sru = new SysRoleUser();
            sru.setUserId(userId);
            sru.setRoleId(roleId);
            list.add(sru);
        }
        if (list.size() > 0) {
            rows = sysRoleUserMapper.insertBatch(list) ? list.size() : 0;
        }
        return rows;
    }

    @Override
    public int cancelAllAuthUser(Long roleId, Long[] userIds) {
        return sysRoleUserMapper.delete(new LambdaQueryWrapper<SysRoleUser>()
                .eq(SysRoleUser::getRoleId, roleId)
                .in(SysRoleUser::getUserId, Arrays.asList(userIds)));
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(Role role) {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleNode> list = new ArrayList<SysRoleNode>();
        for (Long NodeId : role.getMenuIds()) {
            SysRoleNode rm = new SysRoleNode();
            rm.setRoleId(role.getRoleId());
            rm.setNodeId(NodeId);
            list.add(rm);
        }
        if (list.size() > 0) {
            rows = sysRoleNodeMapper.insertBatch(list) ? list.size() : 0;
        }
        return rows;
    }
}
