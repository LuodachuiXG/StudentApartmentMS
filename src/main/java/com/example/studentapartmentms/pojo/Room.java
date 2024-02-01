package com.example.studentapartmentms.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 宿舍房间实体类
 */
@Data
public class Room implements Serializable {
    /** 房间 ID **/
    private Integer roomId;

    /** 宿舍 ID **/
    private Integer dormitoryId;

    /** 房间名 **/
    private String name;

    /** 房间已入住人数 **/
    private Integer headCount;

    /** 房间总床位 **/
    private Integer totalBeds;
}
