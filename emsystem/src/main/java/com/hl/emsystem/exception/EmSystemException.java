
package com.hl.emsystem.exception;

/**
 * 描述：自定义、统一异常；
 */
public class EmSystemException extends RuntimeException {
    private  Integer code;
    private  String message;



    public EmSystemException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public EmSystemException( String message) {

        this.message = message;
    }

    public EmSystemException(EmSystemExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
  