package com.example.studentapartmentms.controller;


import com.example.studentapartmentms.common.JWTUtils;
import com.example.studentapartmentms.common.Utils;
import com.example.studentapartmentms.expcetion.MyException;
import com.example.studentapartmentms.pojo.Pager;
import com.example.studentapartmentms.pojo.RoleEnum;
import com.example.studentapartmentms.pojo.User;
import com.example.studentapartmentms.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@ResponseBody
@Slf4j
public class UserController {


    /** 用户服务接口 **/
    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param user 用户实体类
     * @return 登录成功返回用户信息和 Token
     */
    @PostMapping("/login")
    public ObjectNode login(@RequestBody User user) {
        log.info("LOGIN: " + user.getId());
        return userService.login(user.getId(), user.getPassword());
    }

    /**
     * 用户注册（此接口只能用于管理员注册，学生由管理员添加）
     *
     * @param user 用户实体类
     * @return 注册成功返回用户信息，否则返回 null
     */
    @PostMapping
    public User addUser(@RequestBody User user) {
        log.info("REGISTER: " + user.getId());
        return userService.addUser(user, RoleEnum.ADMIN);
    }


    /**
     * 添加学生（此接口只能由管理员调用）
     *
     * @param user 用户实体类
     * @return 添加成功后返回学生信息，否则返回 null
     */
    @PostMapping("/student")
    public User addStudent(@RequestBody User user) {
        log.info("ADD_STUDENT: " + user.getId());
        return userService.addUser(user, RoleEnum.STUDENT);
    }

    /**
     * 删除用户
     * 管理员只可以删除学生。删除管理员需要自己注销。
     * 学生不可以删除任何人，包括自己
     *
     * @param userIds 学生 ID 集合
     * @return 删除成功返回 true
     */
    @DeleteMapping
    public Boolean deleteUser(
            HttpServletRequest request,
            @RequestBody List<Integer> userIds
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(userService, request, RoleEnum.ADMIN);
        // 获取调用者用户 ID
        int requestUserId = Utils.getUserIdByRequest(request);
        log.info("DELETE_USER: " + userIds.toString());
        return userService.deleteUser(requestUserId, userIds);
    }

    /**
     * 修改用户
     * 管理员不能修改其他管理员信息，但可以修改学生信息
     * 学生只能修改自己的信息
     *
     * @param user 用户实体类
     * @return 修改成功返回 true
     */
    @PutMapping
    public Boolean updateUser(
            HttpServletRequest request,
            @RequestBody User user
    ) {
        // 获取调用者用户 ID
        int requestUserId = Utils.getUserIdByRequest(request);
        // 检查请求参数
        if (user.getUserId() == null ||
                user.getName() == null || user.getName().isBlank() ||
                user.getId() == null || user.getId().isBlank() ||
                user.getPhone() == null || user.getPhone().isBlank() ||
                user.getGender() == null || user.getBirth() == null) {
            // 传参错误
            Utils.throwMismatchParamException();
        }
        log.info("UPDATE_USER: " + user.getUserId());
        return userService.updateUser(requestUserId, user);
    }

    /**
     * 修改用户密码
     * 此接口只能修改密码的用户本人调用
     *
     * @param params 请求参数：oldPwd（旧密码），newPwd（新密码）
     * @return 修改成功返回 true
     */
    @PutMapping("/password")
    public Boolean updateUserPassword(
            HttpServletRequest request,
            @RequestBody Map<String, String> params
    ) {
        // 获取调用者用户 ID
        int requestUserId = Utils.getUserIdByRequest(request);
        // 检查请求参数是否正确
        String oldPwd = params.get("oldPwd");
        String newPwd = params.get("newPwd");
        if (oldPwd == null || newPwd == null) {
            // 请求参数错误
            Utils.throwMismatchParamException();
        }

        log.error("UPDATE_PASSWORD: " + requestUserId);
        return userService.updateUserPassword(requestUserId, oldPwd, newPwd);
    }

    /**
     * 获取所有用户信息
     * 只有管理员可以获取所有用户信息
     */
    @GetMapping
    public List<User> allUser(HttpServletRequest request) {
        // 检查当前用户是否是管理员
        Utils.isRole(userService, request, RoleEnum.ADMIN);
        // 返回所有用户信息
        return userService.allUser();
    }

    /**
     * 分页获取用户信息
     * 只有管理员可以获取所有用户信息
     *
     * @param page 当前页数
     * @param size 每页大小
     */
    @GetMapping("/{page}/{size}")
    public Pager<User> userByPage(
            HttpServletRequest request,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(userService, request, RoleEnum.ADMIN);
        // 返回所有用户信息
        return userService.userByPage(page, size);
    }

    /**
     * 分页和关键词获取用户信息
     * 只有管理员可以获取所有用户信息
     * 关键词：工号（学号）、姓名、电话
     *
     * @param key  查询的关键词
     * @param page 当前页数
     * @param size 每页大小
     */
    @GetMapping("/{key}/{page}/{size}")
    public Pager<User> userByKeyAndPage(
            HttpServletRequest request,
            @PathVariable("key") String key,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(userService, request, RoleEnum.ADMIN);
        // 返回所有用户信息
        return userService.userByKeyAndPage(key, page, size);
    }

}
