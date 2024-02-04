package com.example.studentapartmentms.controller;

import com.example.studentapartmentms.common.Utils;
import com.example.studentapartmentms.pojo.*;
import com.example.studentapartmentms.service.DormitoryService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dorm")
@ResponseBody
@Slf4j
public class DormitoryController {

    /**
     * 宿舍服务接口
     **/
    @Resource
    private DormitoryService dormitoryService;

    /**
     * 获取所有宿舍
     * 仅管理员
     */
    @GetMapping
    public List<Dormitory> allDorm(HttpServletRequest request) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        // 返回所有宿舍
        return dormitoryService.dorms();
    }

    /**
     * 学生获取自己被分配的宿舍房间
     *
     * @param userId 学生用户 ID
     */
    @GetMapping("/student/{userId}")
    public StudentRoomInfo roomByStudentUserId(@PathVariable("userId") Integer userId) {
        // 返回当前学生入住的宿舍
        return dormitoryService.studentRoomByUserId(userId);
    }


    /**
     * 分页获取宿舍
     * 仅管理员
     *
     * @param page 当前页数
     * @param size 每页大小
     */
    @GetMapping("/{page}/{size}")
    public Pager<Dormitory> dormsByPage(
            HttpServletRequest request,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        // 返回分页后的宿舍
        return dormitoryService.dormsByPage(page, size);
    }

    /**
     * 添加宿舍
     * 仅管理员
     *
     * @param names 宿舍名集合
     */
    @PostMapping
    public Boolean addDorms(
            HttpServletRequest request,
            @RequestBody List<String> names
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        log.info("ADD_DORMS: " + names);
        return dormitoryService.addDorms(names);
    }

    /**
     * 删除宿舍
     * 仅管理员
     *
     * @param dormIds 宿舍 ID 集合
     */
    @DeleteMapping
    public Boolean delDorms(
            HttpServletRequest request,
            @RequestBody List<Integer> dormIds
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        log.info("DEL_DORMS: " + dormIds);
        return dormitoryService.deleteDormsByDormIds(dormIds);
    }

    /**
     * 修改宿舍
     * 仅管理员
     *
     * @param dorm 宿舍实体类
     */
    @PutMapping
    public Boolean updateDorm(
            HttpServletRequest request,
            @RequestBody Dormitory dorm
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        log.info("UPDATE_DORM: " + dorm);
        return dormitoryService.updateDorm(dorm);
    }


    /**
     * 添加宿舍管理员
     * 仅管理员
     *
     * @param dorm 宿舍实体类
     */
    @PostMapping("/admin")
    public Boolean addDormAdmins(
            HttpServletRequest request,
            @RequestBody Dormitory dorm
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        log.info("ADD_DORM_ADMINS: " + dorm);
        List<Integer> adminUserIds = new ArrayList<>();
        dorm.getAdmins().forEach(admin -> adminUserIds.add(admin.getUserId()));
        return dormitoryService.addDormAdmins(dorm.getDormitoryId(), adminUserIds);
    }

    /**
     * 删除宿舍管理员
     * 仅管理员
     *
     * @param dorm 宿舍实体类
     */
    @DeleteMapping("/admin")
    public Boolean delDormAdmins(
            HttpServletRequest request,
            @RequestBody Dormitory dorm
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        log.info("DEL_DORM_ADMINS: " + dorm);
        List<Integer> adminUserIds = new ArrayList<>();
        dorm.getAdmins().forEach(admin -> adminUserIds.add(admin.getUserId()));
        return dormitoryService.deleteDormAdminsByDormIdAndUserIds(dorm.getDormitoryId(), adminUserIds);
    }

    /**
     * 根据管理员用户 ID 获取管理的宿舍信息
     * 仅管理员
     *
     * @param userId 管理员用户 ID
     */
    @GetMapping("/admin/{userId}")
    public List<Dormitory> getDormsByAdmin(
            HttpServletRequest request,
            @PathVariable("userId") Integer userId
    ) {
        // 检查调用用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        return dormitoryService.dormsByAdmin(userId);
    }

    /**
     * 根据宿舍楼 ID 获取所有管理员
     *
     * @param dormId 宿舍楼 ID
     */
    @GetMapping("/admin/dorm/{dormId}")
    public List<User> getAdminsByDormId(@PathVariable("dormId") Integer dormId) {
        return dormitoryService.adminsByDormId(dormId);
    }


    /**
     * 添加宿舍房间
     * 仅管理员
     *
     * @param rooms 宿舍房间实体类
     */
    @PostMapping("/room")
    public Boolean addRooms(
            HttpServletRequest request,
            @RequestBody List<Room> rooms
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        log.info("ADD_ROOMS: " + rooms);
        return dormitoryService.addRooms(rooms);
    }

    /**
     * 删除宿舍房间
     * 仅管理员
     *
     * @param roomIds 宿舍房间 ID 集合
     */
    @DeleteMapping("/room")
    public Boolean delRooms(
            HttpServletRequest request,
            @RequestBody List<Integer> roomIds
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        log.info("DEL_ROOMS: " + roomIds);
        return dormitoryService.deleteRoomsByRoomIds(roomIds);
    }

    /**
     * 修改宿舍房间
     * 仅管理员
     *
     * @param room 宿舍房间 ID 集合
     */
    @PutMapping("/room")
    public Boolean updateRoom(
            HttpServletRequest request,
            @RequestBody Room room
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        log.info("UPDATE_ROOM: " + room);
        return dormitoryService.updateRoom(room);
    }

    /**
     * 分页获取宿舍房间
     * 仅管理员
     *
     * @param dormId 宿舍 ID
     * @param page   当前页数
     * @param size   每页大小
     */
    @GetMapping("/room/{dormId}/{page}/{size}")
    public Pager<Room> rooms(
            HttpServletRequest request,
            @PathVariable("dormId") Integer dormId,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        return dormitoryService.roomsByPage(dormId, page, size);
    }

    /**
     * 根据宿舍 ID 获取所有宿舍房间
     * 仅管理员
     *
     * @param dormId 宿舍 ID
     */
    @GetMapping("/room/{dormId}")
    public List<Room> roomsByDormId(
            HttpServletRequest request,
            @PathVariable("dormId") Integer dormId
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        return dormitoryService.roomsByDormId(dormId);
    }


    /**
     * 修改宿舍房间住户
     * 仅管理员
     *
     * @param roomUser 宿舍房间住户实体类
     */
    @PutMapping("/room_user")
    public Boolean updateRoomUsers(
            HttpServletRequest request,
            @RequestBody RoomUser roomUser
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        log.info("UPDATE_ROOM_USERS: " + roomUser);
        return dormitoryService.updateRoomUsers(roomUser);
    }

    /**
     * 修改用户的入住房间
     * 仅管理员
     *
     * @param roomId 宿舍房间 ID
     * @param userId 用户 ID
     */
    @PutMapping("/room/{roomId}/{userId}")
    public Boolean updateRoomUserByRoomIdAndUserId(
            HttpServletRequest request,
            @PathVariable("roomId") Integer roomId,
            @PathVariable("userId") Integer userId
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        log.info("UPDATE_ROOM_USER_BY_USER: roomId: " + roomId + ", userId: " + userId);
        return dormitoryService.updateRoomUserByRoomIdAndUserId(roomId, userId);
    }

    /**
     * 删除宿舍房间住户
     * 仅管理员
     *
     * @param roomUserIds 宿舍房间住户 ID 集合
     */
    @DeleteMapping("/room_user")
    public Boolean delRoomUsers(
            HttpServletRequest request,
            @RequestBody List<Integer> roomUserIds
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        log.info("DEL_ROOM_USERS: " + roomUserIds);
        return dormitoryService.deleteRoomUsersByRoomUserIds(roomUserIds);
    }

    /**
     * 获取宿舍房间住户
     * 仅管理员
     *
     * @param roomId 宿舍房间 ID
     */
    @GetMapping("/room_user/{roomId}")
    public List<User> rooms(
            HttpServletRequest request,
            @PathVariable("roomId") Integer roomId
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        return dormitoryService.roomUsers(roomId);
    }

    /**
     * 根据用户 ID 获取所住的宿舍信息
     * 仅管理员
     *
     * @param userId 用户 ID
     */
    @GetMapping("/room/user/{userId}")
    public Room roomByUserId(
            HttpServletRequest request,
            @PathVariable("userId") Integer userId
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(request, RoleEnum.ADMIN);
        return dormitoryService.roomByUserId(userId);
    }
}
