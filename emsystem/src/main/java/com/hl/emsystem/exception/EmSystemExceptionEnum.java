package com.hl.emsystem.exception;
/**
 * 描述： 异常枚举
 */
public enum EmSystemExceptionEnum {

    SYSTEM_ERROR(20000,"系统异常"),
    USER_ERROR(10001,"用户不存在"),

    PASSWORD_ERROR(10002,"密码错误"),
    PASSWORD_TOO_SHORT(10003,"密码长度不能小于8位"),
    VERIFICATION_CODE_ERROR(10004,"验证码错误"),
    INSERT_ERROR(20001,"发布失败"),
    DELETE_ERROR(20002,"删除失败"),

    REQUEST_PARAM_ERROR(30001,"请求参数错误"),
    UPDATEROLE_FAIL(40001,"修改角色失败，请联系管理员"),
    DELETE_NODE_ERROR(50001,"存在子菜单，不允许删除"),

    NEED_MULU(50002,"请先添加目录，再构建子菜单" ),
    CANCEL_AUTHUSER_FAIL(50003, "取消授权失败"),
    USERNAME_HAVE(50004,"用户昵称已存在" ),
    UPDATE_NODE_ERROR(50005, "存在子菜单，不允许修改为菜单"),
    NOT_DELETE(50006,"当前用户不允许删除");
    /**
     * 异常码
     */
    Integer code;
    /**
     * 异常信息
     */
    String msg;

    EmSystemExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}