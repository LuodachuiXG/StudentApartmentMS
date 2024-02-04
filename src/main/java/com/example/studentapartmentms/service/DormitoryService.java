package com.example.studentapartmentms.service;

import com.example.studentapartmentms.pojo.*;

import java.util.List;

/**
 * 宿舍服务接口
 */
public interface DormitoryService {

    /**
     * 获取所有宿舍
     */
    List<Dormitory> dorms();

    /**
     * 根据管理员用户 ID 获取管理的宿舍信息
     *
     * @param userId 管理员用户 ID
     */
    List<Dormitory> dormsByAdmin(Integer userId);

    /**
     * 分页获取所有宿舍房间
     *
     * @param dormId 宿舍 ID
     * @param page   当前页数
     * @param size   每页大小
     */
    Pager<Room> roomsByPage(Integer dormId, Integer page, Integer size);

    /**
     * 根据宿舍 ID 获取所有宿舍房间
     *
     * @param dormId 宿舍 ID
     */
    List<Room> roomsByDormId(Integer dormId);

    /**
     * 根据用户 ID 获取所住的宿舍信息
     * 仅管理员
     *
     * @param userId 用户 ID
     */
    Room roomByUserId(Integer userId);

    /**
     * 分页获取宿舍
     *
     * @param page 当前页数
     * @param size 每页大小
     */
    Pager<Dormitory> dormsByPage(Integer page, Integer size);

    /**
     * 获取宿舍住户
     *
     * @param roomId 宿舍房间 ID
     */
    List<User> roomUsers(Integer roomId);

    /**
     * 学生获取自己被分配的宿舍房间
     *
     * @param userId 学生用户 ID
     */
    StudentRoomInfo studentRoomByUserId(Integer userId);

    /**
     * 添加宿舍
     *
     * @param names 宿舍名集合
     */
    Boolean addDorms(List<String> names);

    /**
     * 添加宿舍管理员
     *
     * @param dormId  宿舍 ID
     * @param userIds 用户 ID 集合
     */
    Boolean addDormAdmins(Integer dormId, List<Integer> userIds);

    /**
     * 根据宿舍楼 ID 获取所有管理员
     *
     * @param dormId 宿舍楼 ID
     */
    List<User> adminsByDormId(Integer dormId);

    /**
     * 添加宿舍房间
     *
     * @param rooms 房间集合
     */
    Boolean addRooms(List<Room> rooms);


    /**
     * 根据宿舍 ID 删除宿舍
     *
     * @param dormIds 宿舍 ID 集合
     */
    Boolean deleteDormsByDormIds(List<Integer> dormIds);

    /**
     * 根据宿舍管理员用户 ID 和宿舍 ID 删除宿舍管理员
     *
     * @param dormId  宿舍 ID
     * @param userIds 用户 ID 集合
     */
    Boolean deleteDormAdminsByDormIdAndUserIds(Integer dormId, List<Integer> userIds);


    /**
     * 根据宿舍房间 ID 删除宿舍房间
     *
     * @param roomIds 宿舍房间 ID 集合
     */
    Boolean deleteRoomsByRoomIds(List<Integer> roomIds);

    /**
     * 根据宿舍房间住户表 ID 删除宿舍房间住户
     *
     * @param roomUserIds 宿舍房间住户表 ID 集合
     */
    Boolean deleteRoomUsersByRoomUserIds(List<Integer> roomUserIds);

    /**
     * 修改宿舍信息
     *
     * @param dorm 宿舍实体类
     */
    Boolean updateDorm(Dormitory dorm);


    /**
     * 修改宿舍房间信息
     *
     * @param room 宿舍房间实体类
     */
    Boolean updateRoom(Room room);


    /**
     * 修改宿舍房间住户
     *
     * @param roomUser 宿舍房间用户实体类
     */
    Boolean updateRoomUsers(RoomUser roomUser);

    /**
     * 修改用户的入住宿舍
     *
     * @param roomId 宿舍房间 ID
     * @param userId 用户 ID
     */
    Boolean updateRoomUserByRoomIdAndUserId(Integer roomId, Integer userId);

    /**
     * 判断宿舍房间是否满员
     *
     * @param roomId 宿舍房间 ID
     */
    Boolean isRoomFull(Integer roomId);
}
