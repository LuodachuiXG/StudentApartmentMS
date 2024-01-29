package com.example.studentapartmentms.mapper;

import com.example.studentapartmentms.pojo.Dormitory;
import com.example.studentapartmentms.pojo.Room;
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
    int addDorms(List<String> names);

    /**
     * 添加宿舍管理员
     *
     * @param dormId  宿舍 ID
     * @param userIds 用户 ID 集合
     */
    int addDormAdmins(Integer dormId, List<Integer> userIds);

    /**
     * 添加宿舍房间
     *
     * @param dormId 宿舍 ID
     * @param rooms  房间集合
     */
    int addDormRooms(Integer dormId, List<Room> rooms);


    /**
     * 根据宿舍 ID 删除宿舍
     *
     * @param dormIds 宿舍 ID 集合
     */
    int deleteDormsByDormIds(List<Integer> dormIds);

    /**
     * 根据宿舍 ID 删除宿舍管理员
     *
     * @param dormIds 宿舍 ID 集合
     */
    int deleteDormAdminsByDormIds(List<Integer> dormIds);

    /**
     * 根据宿舍管理员 ID 删除宿舍管理员
     *
     * @param dormAdminIds 宿舍管理员 ID 集合
     */
    int deleteDormAdminsByDormAdminIds(List<Integer> dormAdminIds);

    /**
     * 根据用户 ID 删除宿舍管理员
     *
     * @param userIds 用户 ID 集合
     */
    int deleteDormAdminsByUserIds(List<Integer> userIds);

    /**
     * 根据宿舍房间 ID 删除宿舍房间
     *
     * @param roomIds 宿舍房间 ID 集合
     */
    int deleteRoomsByRoomIds(List<Integer> roomIds);

    /**
     * 根据宿舍 ID 删除宿舍房间
     *
     * @param dormIds 宿舍 ID 集合
     */
    int deleteRoomsByDormIds(List<Integer> dormIds);

    /**
     * 根据宿舍房间 ID 删除宿舍房间住户
     *
     * @param roomIds 宿舍房间 ID 集合
     */
    int deleteRoomUsersByRoomIds(List<Integer> roomIds);

    /**
     * 根据用户 ID 删除宿舍房间住户
     *
     * @param userIds 用户 ID 集合
     */
    int deleteRoomUsersByUserIds(List<Integer> userIds);

    /**
     * 修改宿舍信息
     *
     * @param dorm 宿舍实体类
     */
    int updateDorm(Dormitory dorm);

    /**
     * 修改宿舍房间信息
     *
     * @param room 宿舍房间实体类
     */
    int updateRoom(Room room);


    /**
     * 获取所有宿舍
     * 注意：此 SQL 无法填充宿舍管理员字段 admins，需要二次查询手动填充
     *
     * @return 宿舍集合
     */
    List<Dormitory> allDorm();

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
    List<Room> roomByDormId(Integer dormId);

    /**
     * 根据宿舍 ID 获取所有宿舍管理员
     *
     * @param dormId 宿舍 ID
     */
    List<Room> dormAdminByDormId(Integer dormId);

    /**
     * 根据宿舍房间 ID 获取所有宿舍房间居住用户
     *
     * @param roomId 房间 ID
     */
    List<User> roomUserByRoomId(Integer roomId);
}
