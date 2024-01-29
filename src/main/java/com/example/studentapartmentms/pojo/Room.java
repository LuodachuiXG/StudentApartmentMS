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

    /** 房间可容纳人数 **/
    private Integer headCount;
}
