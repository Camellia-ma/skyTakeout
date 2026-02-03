package com.myb.exception;

/**
 * 业务异常基础类 -- 提供无参构造函数以及带异常消息参数的的有参构造和函数
 */
public class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }

}
