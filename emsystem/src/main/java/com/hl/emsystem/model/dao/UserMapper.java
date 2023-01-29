package com.hl.emsystem.model.dao;

import com.hl.emsystem.model.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("UserMapper")
public interface UserMapper extends BaseMapperPlus<UserMapper, User, User>{
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    /**
     * 登录时使用：根据用户名和密码两个条件，查询User
     * @param username
     * @return
     */
    User selectLogin(String username);

    List<User> selectAllUser();


    User selectUserByUsername(String username);
}