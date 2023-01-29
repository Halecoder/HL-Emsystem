package com.hl.emsystem.service;

import com.github.pagehelper.PageInfo;
import com.hl.emsystem.exception.EmSystemException;
import com.hl.emsystem.model.pojo.*;

import java.util.List;


public interface MenuService {

    List <Node> list ();

    void addMenu(Node node);

    Node quaryNode(long nodeCode);

    PageInfo<Role> selectRoleList(Role role, PageQuery pageQuery);

    void updateNode(Node node);

    void deleteNode(Long nodeId) throws EmSystemException;

    Role quaryRole(Long roleId);

    List<Node> selectMenuList(Node menu);

    int updateRole(Role role);

    void addRole(Role role);

    void deleteRole(Long roleId);

    PageInfo<User> selectAllocatedUserList(User user, PageQuery pageQuery);


    int authUserCancel(SysRoleUser roleUser);

    PageInfo<User> selectUnallocatedList(User user, PageQuery pageQuery);

    int authUserSelect(Long roleId, Long[] userIds);

    int cancelAllAuthUser(Long roleId, Long[] userIds);
}
