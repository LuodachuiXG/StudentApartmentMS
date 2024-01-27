package com.example.studentapartmentms.expcetion;

/**
 * 自定义异常类
 */
public class MyException extends RuntimeException {

    private MyException() {}

    public MyException(String errMsg) {
        super(errMsg);
    }
}
