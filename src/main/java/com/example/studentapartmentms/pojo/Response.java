package com.example.studentapartmentms.pojo;

import lombok.Data;

/**
 * 接口响应实体类
 * @param <T> 响应的数据类型
 */
@Data
public class Response<T> {
    /** 响应的 HTTP 代码 **/
    private int code;

    /** 错误信息 **/
    private String errMsg;

    /** 实际响应数据 **/
    private T data;
}
