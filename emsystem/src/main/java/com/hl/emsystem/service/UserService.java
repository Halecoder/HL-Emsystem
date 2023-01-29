package com.hl.emsystem.service;

import com.github.pagehelper.PageInfo;
import com.hl.emsystem.exception.EmSystemException;
import com.hl.emsystem.model.pojo.PageQuery;
import com.hl.emsystem.model.pojo.Role;
import com.hl.emsystem.model.pojo.User;

import java.util.List;


public interface UserService {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password) throws EmSystemException;


    PageInfo<User> selectlistUser(User user, PageQuery pageQuery);

    List<Role> selectAllRole();

    User selectByUserId(Long userId);

    void addUserInfo(User user);


    void deleteUserInfo(Long userId);

    boolean checkUserNameHave(User user);

    List<Long> selectAllRoleIdByUserId(Long userId);

    void updateUserInfo(User user);

    void resetPwd(User user, String password);

    User selectUserByUsername(String username);

    int deleteAllUserInfo(Long[] userIds);

    List<User> exportAllUser(User user);
}
