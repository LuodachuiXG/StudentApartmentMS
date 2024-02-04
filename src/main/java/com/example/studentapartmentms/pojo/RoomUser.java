package com.example.studentapartmentms.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 宿舍房间用户表实体类
 */
@Data
public class RoomUser implements Serializable {

    /** 宿舍房间用户表  ID **/
    private Integer roomUserId;

    /** 宿舍房间 ID **/
    private Integer roomId;

    /** 用户 ID 集合 **/
    private List<Integer> userIds;
}
