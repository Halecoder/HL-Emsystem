package com.hl.emsystem.common;


import org.springframework.stereotype.Component;


/**
 * 描述：存放变量
 */
@Component
public class Constant {

    public static  final String SALT = "hdfjwkefhkwsdwd,[cjicvo";//盐值
    public static final String JWT_KEY = "EmSystem";
    public static final String USER_NAME = "username";
    public static final Long EXPIRE_TIME = 60 * 1000 * 60 * 24 * 1000L;//单位是毫秒

    public static final String  TOKEN = "token";
    public static final String USER_ID = "userId";
    public static final String PEOPLE_ID = "peopleId";
    public static final String NAME = "name" ;

//    登陆网页
    public static final String URL = "http://localhost:9528";
}
