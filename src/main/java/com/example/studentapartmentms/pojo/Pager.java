package com.example.studentapartmentms.pojo;

import lombok.Data;

import java.util.List;

/**
 * 分页实体类
 * 
 */
@Data
public class Pager<T> {
    /** 当前页 **/
    private Integer page;

    /** 每页记录数 **/
    private Integer size;

    /** 数据集合 **/
    private List<T> data;

    /** 总数据数 **/
    private Long totalData;

    /** 总页数 **/
    private Integer totalPage;
}
