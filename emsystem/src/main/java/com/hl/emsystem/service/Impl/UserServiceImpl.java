package com.hl.emsystem.service.Impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hl.emsystem.exception.EmSystemException;
import com.hl.emsystem.exception.EmSystemExceptionEnum;
import com.hl.emsystem.model.dao.*;
import com.hl.emsystem.model.pojo.*;
import com.hl.emsystem.service.UserService;
import com.hl.emsystem.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    PeopleMapper peopleMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    SysRoleUserMapper sysRoleUserMapper;

    @Override
    public User login(String username, String password) throws EmSystemException {
        String MD5Password = null;
        try {
            MD5Password = MD5Utils.getMD5String(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(username);
        User usertemp = userMapper.selectUserByUsername(username);
        user.setName(usertemp.getName());
        if (user == null) {
            throw new EmSystemException(EmSystemExceptionEnum.USER_ERROR);
        }
        if (!MD5Password.equals(user.getPassword())) {
            throw new EmSystemException(EmSystemExceptionEnum.PASSWORD_ERROR);
        }
        return user;
    }
    @Override
    public PageInfo<User> selectlistUser(User user, PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        List<User> users =  userMapper.selectAllUser();
        PageInfo pageInfo = new PageInfo<>(users);
//        pageInfo.setList(users);
        return pageInfo;

    }

    @Override
    public List<Role> selectAllRole() {
        return roleMapper.selectAll();
    }

    @Override
    public User selectByUserId(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }



//    涉及多表操作，添加事务回滚
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserInfo(User user) {
//        涉及三张表的插入 单位表，部门表，用户信息表，现在不用，以后用
        //插入单位表(获取单位id)->插入部门(带上单位id)->插入用户信息(带上部门id)
        Department department = new Department();
        department.setDepartmentName(user.getDepartmentName());
        departmentMapper.insertSelective(department);
        People people = new People();
        people.setName(user.getName());
        people.setDepartmentId(department.getDepartmentId());
        peopleMapper.insertSelective(people);
         user.setPeopleId(people.getPeopleId());
        String MD5Password = null;
        try {
            MD5Password = MD5Utils.getMD5String(user.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
         user.setPassword(MD5Password);
        userMapper.insertSelective(user);

//        将用户和角色关联
        insertUserRole(user.getUserId(),user.getRoleIds());

    }

    public void insertUserRole(Long userId, Long[] roleIds) {
        if (ArrayUtil.isNotEmpty(roleIds)) {
            // 新增用户与角色管理
            List<SysRoleUser> list = new ArrayList<>(roleIds.length);
            for (Long roleId : roleIds) {
                SysRoleUser sru = new SysRoleUser();
                sru.setUserId(userId);
                sru.setRoleId(roleId);
                list.add(sru);
            }
            sysRoleUserMapper.insertBatch(list);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserInfo(Long userId) {

//        删除关联用户信息
        //用户信息表-->部门-->单位
        User user = userMapper.selectByPrimaryKey(userId);
        userMapper.deleteByPrimaryKey(userId);
        People  people = peopleMapper.selectByPrimaryKey(user.getPeopleId());
        peopleMapper.deleteByPrimaryKey(user.getPeopleId());
        departmentMapper.deleteByPrimaryKey(people.getDepartmentId());

//        删除关联角色信息

        sysRoleUserMapper.delete(new LambdaQueryWrapper<SysRoleUser>().eq(SysRoleUser::getUserId, userId));

    }

    @Override
    public boolean checkUserNameHave(User user) {
        boolean isHave = userMapper.exists(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, user.getUsername()));
        return isHave;
    }

    @Override
    public List<Long> selectAllRoleIdByUserId(Long userId) {
       List<Long> roleIds = sysRoleUserMapper.selectRoleIdByUserId(userId);
       return roleIds;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(User user) {
        // 删除用户与角色关联
        sysRoleUserMapper.delete(new LambdaQueryWrapper<SysRoleUser>().eq(SysRoleUser::getUserId, user.getUserId()));
        // 新增用户与角色管理
        insertUserRole(user.getUserId(), user.getRoleIds());
        //更新用户信息
        String MD5Password = null;
        try {
           User userTemp =  userMapper.selectByPrimaryKey(user.getUserId());
           if(!(user.getPassword().equals(userTemp.getPassword()))){//说明传来的密码未加密过
               MD5Password = MD5Utils.getMD5String(user.getPassword());
           }else {
               MD5Password = user.getPassword();
           }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        user.setPassword(MD5Password);
        userMapper.updateByPrimaryKeySelective(user);
        People people = new People();
        people.setName(user.getName());
        people.setPeopleId(user.getPeopleId());
        peopleMapper.updateByPrimaryKeySelective(people);

//        获取people信息
        People peopleTemp = peopleMapper.selectByPrimaryKey(people.getPeopleId());
        Department department = new Department();
        department.setDepartmentId(peopleTemp.getDepartmentId());
        department.setDepartmentName(user.getDepartmentName());
        departmentMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public void resetPwd(User user, String password) {
        String MD5Password = null;
        try {
            MD5Password = MD5Utils.getMD5String(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        user.setPassword(MD5Password);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAllUserInfo(Long[] userIds) {
        ArrayList<Long> Dids =new ArrayList<Long>();
        List<Long> ids = Arrays.asList(userIds);
       int isdelete =  userMapper.delete(new LambdaQueryWrapper<User>().in(User::getUserId, ids));
        for(Long userId:userIds){
          Long  did =  peopleMapper.selectByPrimaryKey(userId).getDepartmentId();
            Dids.add(did);
        }
        //删除用户信息
        peopleMapper.delete(new LambdaQueryWrapper<People>().in(People::getPeopleId, ids));
        //删除部门信息
        departmentMapper.delete(new LambdaQueryWrapper<Department>().in(Department::getDepartmentId,Dids));
        // 删除用户与角色关联
        sysRoleUserMapper.delete(new LambdaQueryWrapper<SysRoleUser>().in(SysRoleUser::getUserId, ids));
        return isdelete;
    }

    @Override
    public List<User> exportAllUser(User user) {
        return userMapper.selectAllUser();
    }




}

