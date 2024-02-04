package com.example.studentapartmentms.mapper;

import com.example.studentapartmentms.pojo.Dormitory;
import com.example.studentapartmentms.pojo.Room;
import com.example.studentapartmentms.pojo.RoomUser;
import com.example.studentapartmentms.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 宿舍表 Mapper 接口
 */
@Mapper
public interface DormitoryMapper {

    /**
     * 添加宿舍
     *
     * @param names 宿舍名集合
     */
    int addDorms(@Param("names") List<String> names);

    /**
     * 添加宿舍管理员
     *
     * @param dormId  宿舍 ID
     * @param userIds 用户 ID 集合
     */
    int addDormAdmins(@Param("dormId") Integer dormId,
                      @Param("userIds") List<Integer> userIds);

    /**
     * 添加宿舍房间
     *
     * @param rooms 房间集合
     */
    int addRooms(@Param("rooms") List<Room> rooms);

    /**
     * 添加宿舍房间住户
     *
     * @param roomUser 宿舍房间住户实体类
     */
    int addRoomUsers(@Param("roomUser") RoomUser roomUser);


    /**
     * 根据宿舍 ID 删除宿舍
     *
     * @param dormIds 宿舍 ID 集合
     */
    int deleteDormsByDormIds(@Param("dormIds") List<Integer> dormIds);

    /**
     * 根据宿舍 ID 删除宿舍管理员
     *
     * @param dormIds 宿舍 ID 集合
     */
    int deleteDormAdminsByDormIds(@Param("dormIds") List<Integer> dormIds);

    /**
     * 根据宿舍管理员用户 ID 和宿舍 ID 删除宿舍管理员
     *
     * @param dormId  宿舍 ID
     * @param userIds 用户 ID 集合
     */
    int deleteDormAdminsByDormIdAndUserIds(@Param("dormId") Integer dormId,
                                           @Param("userIds") List<Integer> userIds);

    /**
     * 根据用户 ID 删除宿舍管理员
     *
     * @param userIds 用户 ID 集合
     */
    int deleteDormAdminsByUserIds(@Param("userIds") List<Integer> userIds);

    /**
     * 根据宿舍房间 ID 删除宿舍房间
     *
     * @param roomIds 宿舍房间 ID 集合
     */
    int deleteRoomsByRoomIds(@Param("roomIds") List<Integer> roomIds);

    /**
     * 根据宿舍 ID 删除宿舍房间
     *
     * @param dormIds 宿舍 ID 集合
     */
    int deleteRoomsByDormIds(@Param("dormIds") List<Integer> dormIds);

    /**
     * 根据宿舍房间 ID 集合删除宿舍房间住户
     *
     * @param roomIds 宿舍房间 ID 集合
     */
    int deleteRoomUsersByRoomIds(@Param("roomIds") List<Integer> roomIds);

    /**
     * 根据用户 ID 删除宿舍房间住户
     *
     * @param userIds 用户 ID 集合
     */
    int deleteRoomUsersByUserIds(@Param("userIds") List<Integer> userIds);

    /**
     * 根据宿舍 ID 集合删除这些宿舍所有房间的住户
     *
     * @param dormIds 宿舍 ID 集合
     */
    int deleteRoomUsersByDormIds(@Param("dormIds") List<Integer> dormIds);

    /**
     * 根据宿舍房间住户表 ID 删除宿舍房间住户
     *
     * @param roomUserIds 宿舍房间住户表 ID 集合
     */
    int deleteRoomUsersByRoomUserIds(@Param("roomUserIds") List<Integer> roomUserIds);

    /**
     * 修改宿舍信息
     *
     * @param dorm 宿舍实体类
     */
    int updateDorm(@Param("dorm") Dormitory dorm);

    /**
     * 修改宿舍房间信息
     *
     * @param room 宿舍房间实体类
     */
    int updateRoom(@Param("room") Room room);


    /**
     * 获取所有宿舍
     * 注意：此 SQL 无法填充宿舍管理员字段 admins，需要二次查询手动填充
     */
    List<Dormitory> dorms();

    /**
     * 根据宿舍名获取宿舍
     * 用于验证宿舍名是否存在
     *
     * @param names 宿舍名集合
     */
    List<Dormitory> dormsByNames(@Param("names") List<String> names);

    /**
     * 根据宿舍 ID 获取宿舍管理员
     *
     * @param dormitoryId 宿舍 ID
     * @return 宿舍管理员用户集合
     */
    List<User> adminsByDormitoryId(@Param("dormitoryId") Integer dormitoryId);


    /**
     * 根据宿舍 ID 获取所有宿舍房间
     *
     * @param dormId 宿舍 ID
     */
    List<Room> roomsByDormId(@Param("dormId") Integer dormId);

    /**
     * 根据宿舍 ID 和宿舍房间名集合获取所有宿舍房间
     *
     * @param dormId    宿舍 ID
     * @param roomNames 宿舍房间名集合
     */
    List<Room> roomsByDormIdAndRoomNames(@Param("dormId") Integer dormId,
                                         @Param("roomNames") List<String> roomNames);

    /**
     * 根据宿舍 ID 获取所有宿舍管理员
     *
     * @param dormId 宿舍 ID
     */
    List<User> dormAdminByDormId(@Param("dormId") Integer dormId);

    /**
     * 根据管理员用户 ID 获取管理的宿舍信息
     *
     * @param userId 管理员用户 ID
     */
    List<Dormitory> dormsByAdmin(@Param("userId") Integer userId);


    /**
     * 根据宿舍楼 ID 获取宿舍
     *
     * @param dormId 宿舍楼 ID
     */
    Dormitory dormByDormId(@Param("dormId") Integer dormId);

    /**
     * 根据宿舍房间 ID 获取所有宿舍房间居住用户
     *
     * @param roomId 房间 ID
     */
    List<User> roomUsersByRoomId(@Param("roomId") Integer roomId);

    /**
     * 根据用户 ID 获取所住的宿舍房间
     * 仅管理员
     *
     * @param userId 用户 ID
     */
    Room roomByUserId(@Param("userId") Integer userId);

    /**
     * 根据宿舍房间 ID 获取宿舍房间
     * 仅管理员
     *
     * @param roomId 宿舍房间 ID
     */
    Room roomByRoomId(@Param("roomId") Integer roomId);
}
