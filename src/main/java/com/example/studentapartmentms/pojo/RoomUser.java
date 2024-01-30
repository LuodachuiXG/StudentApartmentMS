package com.example.studentapartmentms.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 宿舍房间用户表实体类
 */
@Data
public class RoomUser implements Serializable {

    /** 宿舍房间用户表  ID **/
    private Integer roomUserId;

    /** 宿舍房间 ID **/
    private Integer roomId;

    /** 用户 ID **/
    private Integer userId;
}
