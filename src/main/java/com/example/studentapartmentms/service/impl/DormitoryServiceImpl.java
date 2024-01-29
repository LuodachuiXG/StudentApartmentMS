package com.example.studentapartmentms.service.impl;

import com.example.studentapartmentms.common.Utils;
import com.example.studentapartmentms.mapper.DormitoryMapper;
import com.example.studentapartmentms.pojo.Dormitory;
import com.example.studentapartmentms.pojo.Pager;
import com.example.studentapartmentms.pojo.Room;
import com.example.studentapartmentms.pojo.User;
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
        List<Dormitory> dorms = dormitoryMapper.allDorm();

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
    public Pager<Room> dormRoomsByPage(Integer dormId, Integer page, Integer size) {
        // 开始分页查询，执行此代码之后的 SQL 会被自动加上分页的代码
        PageHelper.startPage(page, size);
        // 此处的获取所有宿舍的 SQL 已经被加上了分页代码
        List<Room> list = dormitoryMapper.roomByDormId(dormId);
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
     * 添加宿舍
     *
     * @param names 宿舍名集合
     */
    @Override
    public Boolean addDorms(List<String> names) {
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
     * @param dormId 宿舍 ID
     * @param rooms  房间集合
     */
    @Override
    public Boolean addDormRooms(Integer dormId, List<Room> rooms) {
        return dormitoryMapper.addDormRooms(dormId, rooms) > 0;
    }

    /**
     * 根据宿舍 ID 删除宿舍
     *
     * @param dormIds 宿舍 ID 集合
     */
    @Override
    public Boolean deleteDormsByDormIds(List<Integer> dormIds) {
        return dormitoryMapper.deleteDormsByDormIds(dormIds) > 0;
    }

    /**
     * 根据宿舍管理员 ID 删除宿舍管理员
     *
     * @param dormAdminIds 宿舍管理员 ID 集合
     */
    @Override
    public Boolean deleteDormAdminsByDormAdminIds(List<Integer> dormAdminIds) {
        return dormitoryMapper.deleteDormAdminsByDormAdminIds(dormAdminIds) > 0;
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
     * 根据用户 ID 删除宿舍房间住户
     *
     * @param userIds 用户 ID 集合
     */
    @Override
    public Boolean deleteRoomUsersByUserIds(List<Integer> userIds) {
        return dormitoryMapper.deleteRoomUsersByUserIds(userIds) > 0;
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
}
