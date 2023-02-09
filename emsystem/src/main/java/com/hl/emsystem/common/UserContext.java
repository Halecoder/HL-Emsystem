package com.hl.emsystem.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取当前用户信息的辅助类
 *
 * @author xtoad
 * @date 2021/02/19
 */
public class UserContext {

    private static final long serialVersionUID = 1L;

    private static InheritableThreadLocal<Map<String, Object>> threadLocal;



    /**
     * 用户名
     */
    public static final String CONTEXT_KEY_USER_NAME = "username";

    /**
     * 用户密码
     */
    public static final String CONTEXT_KEY_USER_PASSWORD="password";

    /**
     * 验证码
     */
    public static final String CONTEXT_KEY_USER_CODE="code";




    static {
            threadLocal = new InheritableThreadLocal<>();

        }


    /**
     * 设置数据
     *
     * @param key 键
     * @param value 值
     */
    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(6);
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(6);
            threadLocal.set(map);
        }
        return map.get(key);
    }

    /**
     * 清除数据
     *
     */
    public static void remove() {
        threadLocal.remove();
    }


    public static void setUserName(String userName) {
        set(CONTEXT_KEY_USER_NAME, userName);
    }

    public static String getUserName() {
        Object value = get(CONTEXT_KEY_USER_NAME);
        return String.valueOf(value);
    }

    public static void setPassword(String password) {
        set(CONTEXT_KEY_USER_PASSWORD, password);
    }

    public static String getPassword() {
        Object value = get(CONTEXT_KEY_USER_PASSWORD);
        return String.valueOf(value);
    }

    public static void setCode(String code) {
        set(CONTEXT_KEY_USER_CODE, code);
    }

    public static String getCode() {
        Object value = get(CONTEXT_KEY_USER_CODE);
        return String.valueOf(value);
    }

}