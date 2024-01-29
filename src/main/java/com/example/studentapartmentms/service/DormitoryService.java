package com.example.studentapartmentms.service;

import com.example.studentapartmentms.pojo.Dormitory;
import com.example.studentapartmentms.pojo.Pager;
import com.example.studentapartmentms.pojo.Room;
import com.example.studentapartmentms.pojo.User;

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
     * 分页获取所有宿舍房间
     *
     * @param dormId 宿舍 ID
     * @param page   当前页数
     * @param size   每页大小
     */
    Pager<Room> dormRoomsByPage(Integer dormId, Integer page, Integer size);

    /**
     * 分页获取宿舍
     *
     * @param page 当前页数
     * @param size 每页大小
     */
    Pager<Dormitory> dormsByPage(Integer page, Integer size);

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
     * 添加宿舍房间
     *
     * @param dormId 宿舍 ID
     * @param rooms  房间集合
     */
    Boolean addDormRooms(Integer dormId, List<Room> rooms);


    /**
     * 根据宿舍 ID 删除宿舍
     *
     * @param dormIds 宿舍 ID 集合
     */
    Boolean deleteDormsByDormIds(List<Integer> dormIds);

    /**
     * 根据宿舍管理员 ID 删除宿舍管理员
     *
     * @param dormAdminIds 宿舍管理员 ID 集合
     */
    Boolean deleteDormAdminsByDormAdminIds(List<Integer> dormAdminIds);


    /**
     * 根据宿舍房间 ID 删除宿舍房间
     *
     * @param roomIds 宿舍房间 ID 集合
     */
    Boolean deleteRoomsByRoomIds(List<Integer> roomIds);

    /**
     * 根据用户 ID 删除宿舍房间住户
     *
     * @param userIds 用户 ID 集合
     */
    Boolean deleteRoomUsersByUserIds(List<Integer> userIds);

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
}
