package com.example.studentapartmentms.service.impl;

import com.example.studentapartmentms.common.Utils;
import com.example.studentapartmentms.expcetion.MyException;
import com.example.studentapartmentms.mapper.DormitoryMapper;
import com.example.studentapartmentms.pojo.*;
import com.example.studentapartmentms.service.DormitoryService;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     * 根据管理员用户 ID 获取管理的宿舍信息
     *
     * @param userId 管理员用户 ID
     */
    @Override
    public List<Dormitory> dormsByAdmin(Integer userId) {
        return dormitoryMapper.dormsByAdmin(userId);
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
        // 二次查询每个房间的住户
        list.forEach(room -> {
            List<User> users = dormitoryMapper.roomUsersByRoomId(room.getRoomId());
            room.setUsers(users);
        });
        return Utils.getPager(list, page, size);
    }

    /**
     * 根据宿舍 ID 获取所有宿舍房间
     *
     * @param dormId 宿舍 ID
     */
    @Override
    public List<Room> roomsByDormId(Integer dormId) {
        return dormitoryMapper.roomsByDormId(dormId);
    }

    /**
     * 根据用户 ID 获取所住的宿舍信息
     * 仅管理员
     *
     * @param userId 用户 ID
     */
    @Override
    public Room roomByUserId(Integer userId) {
        return dormitoryMapper.roomByUserId(userId);
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
            dorms.forEach(name -> {
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
     * 根据宿舍楼 ID 获取所有管理员
     *
     * @param dormId 宿舍楼 ID
     */
    @Override
    public List<User> adminsByDormId(Integer dormId) {
        return dormitoryMapper.adminsByDormitoryId(dormId);
    }

    /**
     * 添加宿舍房间
     *
     * @param rooms 房间集合
     */
    @Override
    public Boolean addRooms(List<Room> rooms) {
        // 先判断当前宿舍楼是否已经存在相同名称的宿舍房间
        List<String> roomNames = new ArrayList<>();
        rooms.forEach(room -> {
            // 将房间名加入待检查集合
            roomNames.add(room.getName());
            // 同时判断是否有房间总床位非法
            if (room.getTotalBeds() <= 0) {
                throw new MyException("房间总床位值非法");
            }
        });
        List<Room> selectRes = dormitoryMapper.roomsByDormIdAndRoomNames(rooms.get(0).getDormitoryId(), roomNames);
        if (selectRes != null && !selectRes.isEmpty()) {
            // 有重名宿舍房间，获取已经存在的房间名
            StringBuilder existRoomNames = new StringBuilder();
            selectRes.forEach(room -> existRoomNames.append(room.getName()).append(" "));
            throw new MyException("房间名：[ " + existRoomNames + "]已经存在");
        }
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
     * @param dormId  宿舍 ID
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
        // 首先根据房间 ID 删除当前房间的所有住户
        dormitoryMapper.deleteRoomUsersByRoomIds(roomIds);
        // 删除房间
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
        // 判断宿舍名是否已经存在
        List<Dormitory> dorms = dormitoryMapper.dormsByNames(List.of(dorm.getName()));
        // 按宿舍名查找结果不为空
        if (!dorms.isEmpty()) {
            // 如果查询结果大小为 1，且该宿舍 ID 不是当前的宿舍 ID 的话，证明宿舍名已存在
            if (dorms.size() == 1 && !dorms.get(0).getDormitoryId().equals(dorm.getDormitoryId())) {
                // 宿舍名已经存在
                throw new MyException("宿舍名：" + dorm.getName() + " 已存在");
            }
        }
        return dormitoryMapper.updateDorm(dorm) > 0;
    }

    /**
     * 修改宿舍房间信息
     *
     * @param room 宿舍房间实体类
     */
    @Override
    public Boolean updateRoom(Room room) {
        // 修改房间信息前检查房间名是否已经存在
        List<Room> rs = dormitoryMapper.roomsByDormIdAndRoomNames(room.getDormitoryId(), List.of(room.getName()));
        if (rs != null && !rs.isEmpty() && !rs.get(0).getRoomId().equals(room.getRoomId())) {
            throw new MyException("宿舍房间：" + room.getName() + " 已经存在");
        }

        // 判断房间总床位数量是否非法
        if (room.getTotalBeds() <= 0) {
            throw new MyException("房间总床位值非法");
        }

        // 检查房间总床位大小是否小于已经入住学生数量
        List<User> students = roomUsers(room.getRoomId());
        if (room.getTotalBeds() < students.size()) {
            throw new MyException("总床位数不能小于已经入住学生数");
        }

        // 修改宿舍房间
        return dormitoryMapper.updateRoom(room) > 0;
    }

    /**
     * 修改宿舍房间住户
     *
     * @param roomUser 宿舍房间用户实体类
     */
    @Override
    public Boolean updateRoomUsers(RoomUser roomUser) {
        if (roomUser.getUserIds() == null || roomUser.getUserIds().isEmpty()) {
            // 传过来的用户数组为空，删除当前宿舍所有用户
            return dormitoryMapper.deleteRoomUsersByRoomIds(List.of(roomUser.getRoomId())) > 0;
        } else {
            // 检查传入的用户是否大于宿舍总床位
            Room room = dormitoryMapper.roomByRoomId(roomUser.getRoomId());
            if (roomUser.getUserIds().size() > room.getTotalBeds()) {
                throw new MyException("当前宿舍只能容纳最多 " + room.getTotalBeds() + " 人");
            }

            // 根据当前传过来的新的入住的用户，删除当前用户其他入住数据
            dormitoryMapper.deleteRoomUsersByUserIds(roomUser.getUserIds());
            // 删除当前宿舍所有住户
            dormitoryMapper.deleteRoomUsersByRoomIds(List.of(roomUser.getRoomId()));
            // 添加宿舍房间住户
            return dormitoryMapper.addRoomUsers(roomUser) > 0;
        }
    }

    /**
     * 修改用户的入住宿舍
     *
     * @param roomId 宿舍房间 ID
     * @param userId 用户 ID
     */
    @Override
    public Boolean updateRoomUserByRoomIdAndUserId(Integer roomId, Integer userId) {
        // 首先检查目标宿舍房间是否满员
        if (isRoomFull(roomId)) {
            throw new MyException("目标宿舍房间已经满员");
        }

        // 先删除当前用户其他入住信息
        dormitoryMapper.deleteRoomUsersByUserIds(List.of(userId));
        // 用户入住指定房间
        RoomUser rs = new RoomUser();
        rs.setRoomId(roomId);
        rs.setUserIds(List.of(userId));
        return dormitoryMapper.addRoomUsers(rs) > 0;
    }

    /**
     * 判断宿舍房间是否满员
     *
     * @param roomId 宿舍房间 ID
     */
    @Override
    public Boolean isRoomFull(Integer roomId) {
        // 获取宿舍信息
        Room room = dormitoryMapper.roomByRoomId(roomId);
        // 获取当前宿舍住户
        List<User> users = dormitoryMapper.roomUsersByRoomId(roomId);
        return users.size() >= room.getTotalBeds();
    }
}
