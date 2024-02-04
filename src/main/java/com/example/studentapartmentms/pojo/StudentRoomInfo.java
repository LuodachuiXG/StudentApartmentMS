package com.example.studentapartmentms.pojo;

import lombok.Data;

import java.util.List;

/**
 * 学生入住房间信息实体类
 */
@Data
public class StudentRoomInfo {
    /** 宿舍楼名 **/
    private String dormName;
    /** 房间名 **/
    private String roomName;
    /** 房间总床位 **/
    private Integer totalBeds;
    /** 宿舍楼管理员 **/
    private List<User> admins;
    /** 舍友 **/
    private List<User> roomMates;
}
