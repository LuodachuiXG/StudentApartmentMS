package com.example.studentapartmentms.service.impl;

import com.example.studentapartmentms.common.Utils;
import com.example.studentapartmentms.expcetion.MyException;
import com.example.studentapartmentms.mapper.DormitoryMapper;
import com.example.studentapartmentms.pojo.*;
import com.example.studentapartmentms.service.DormitoryService;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 宿舍服务接口实现类
 */
@Service
public class DormitoryServiceImpl implements DormitoryService {

    @Resource
    private DormitoryMapper dormitoryMapper;

    /**
     * 获取所有宿舍
     * 仅管理员
     *
     * @return 宿舍集合
     */
    @Override
    public List<Dormitory> dorms() {
        // 获取所有宿舍。此 Dormitory 实体类不包含 admins 字段，需二次查询
        List<Dormitory> dorms = dormitoryMapper.dorms();

        // 查询每个宿舍的管理员
        dorms.forEach(dorm -> {
            List<User> admins = dormitoryMapper.adminsByDormitoryId(dorm.getDormitoryId());
            dorm.setAdmins(admins);
        });
        return dorms;
    }

    /**
     * 分页获取所有宿舍房间
     *
     * @param dormId 宿舍 ID
     * @param page   当前页数
     * @param size   每页大小
     */
    @Override
    public Pager<Room> roomsByPage(Integer dormId, Integer page, Integer size) {
        // 开始分页查询，执行此代码之后的 SQL 会被自动加上分页的代码
        PageHelper.startPage(page, size);
        // 此处的获取所有宿舍的 SQL 已经被加上了分页代码
        List<Room> list = dormitoryMapper.roomsByDormId(dormId);
        return Utils.getPager(list, page, size);
    }

    /**
     * 分页获取宿舍
     *
     * @param page 当前页数
     * @param size 每页大小
     */
    @Override
    public Pager<Dormitory> dormsByPage(Integer page, Integer size) {
        // 开始分页查询，执行此代码之后的 SQL 会被自动加上分页的代码
        PageHelper.startPage(page, size);
        // 此处的获取所有宿舍的 SQL 已经被加上了分页代码
        List<Dormitory> list = dorms();
        return Utils.getPager(list, page, size);
    }

    /**
     * 获取宿舍住户
     *
     * @param roomId 宿舍房间 ID
     */
    @Override
    public List<User> roomUsers(Integer roomId) {
        return dormitoryMapper.roomUsersByRoomId(roomId);
    }

    /**
     * 添加宿舍
     *
     * @param names 宿舍名集合
     */
    @Override
    public Boolean addDorms(List<String> names) {
        // 检查当前宿舍名是否重复
        List<Dormitory> dorms = dormitoryMapper.dormsByNames(names);
        // 待添加宿舍名有重复
        if (dorms != null && !dorms.isEmpty()) {
            StringBuilder existName = new StringBuilder();
            dorms.forEach( name -> {
                existName.append(names).append(" ");
            });
            throw new MyException("宿舍名：" + existName + "已存在");
        }
        return dormitoryMapper.addDorms(names) > 0;
    }

    /**
     * 添加宿舍管理员
     *
     * @param dormId  宿舍 ID
     * @param userIds 用户 ID 集合
     */
    @Override
    public Boolean addDormAdmins(Integer dormId, List<Integer> userIds) {
        return dormitoryMapper.addDormAdmins(dormId, userIds) > 0;
    }

    /**
     * 添加宿舍房间
     *
     * @param rooms  房间集合
     */
    @Override
    public Boolean addRooms(List<Room> rooms) {
        return dormitoryMapper.addRooms(rooms) > 0;
    }

    /**
     * 根据宿舍 ID 删除宿舍
     *
     * @param dormIds 宿舍 ID 集合
     */
    @Override
    public Boolean deleteDormsByDormIds(List<Integer> dormIds) {
        // 根据宿舍 ID 集合删除宿舍管理员
        dormitoryMapper.deleteDormAdminsByDormIds(dormIds);
        // 根据宿舍 ID 集合删除宿舍的所有房间的住户
        dormitoryMapper.deleteRoomUsersByDormIds(dormIds);
        // 根据宿舍 ID 集合删除宿舍的所有房间
        dormitoryMapper.deleteRoomsByDormIds(dormIds);
        // 删除宿舍
        return dormitoryMapper.deleteDormsByDormIds(dormIds) > 0;
    }

    /**
     * 根据宿舍管理员用户 ID 和宿舍 ID 删除宿舍管理员
     *
     * @param dormId 宿舍 ID
     * @param userIds 用户 ID 集合
     */
    @Override
    public Boolean deleteDormAdminsByDormIdAndUserIds(Integer dormId, List<Integer> userIds) {
        return dormitoryMapper.deleteDormAdminsByDormIdAndUserIds(dormId, userIds) > 0;
    }

    /**
     * 根据宿舍房间 ID 删除宿舍房间
     *
     * @param roomIds 宿舍房间 ID 集合
     */
    @Override
    public Boolean deleteRoomsByRoomIds(List<Integer> roomIds) {
        return dormitoryMapper.deleteRoomsByRoomIds(roomIds) > 0;
    }

    /**
     * 根据宿舍房间住户表 ID 删除宿舍房间住户
     *
     * @param roomUserIds 宿舍房间住户表 ID 集合
     */
    @Override
    public Boolean deleteRoomUsersByRoomUserIds(List<Integer> roomUserIds) {
        return dormitoryMapper.deleteRoomUsersByRoomUserIds(roomUserIds) > 0;
    }

    /**
     * 修改宿舍信息
     *
     * @param dorm 宿舍实体类
     */
    @Override
    public Boolean updateDorm(Dormitory dorm) {
        return dormitoryMapper.updateDorm(dorm) > 0;
    }

    /**
     * 修改宿舍房间信息
     *
     * @param room 宿舍房间实体类
     */
    @Override
    public Boolean updateRoom(Room room) {
        return dormitoryMapper.updateRoom(room) > 0;
    }

    /**
     * 添加宿舍房间住户
     *
     * @param roomUsers 宿舍房间用户实体类集合
     */
    @Override
    public Boolean addRoomUsers(List<RoomUser> roomUsers) {
        return dormitoryMapper.addRoomUsers(roomUsers) > 0;
    }
}
