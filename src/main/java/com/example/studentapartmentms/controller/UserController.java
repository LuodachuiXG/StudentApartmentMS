package com.example.studentapartmentms.controller;


import com.example.studentapartmentms.common.Utils;
import com.example.studentapartmentms.pojo.Pager;
import com.example.studentapartmentms.pojo.RoleEnum;
import com.example.studentapartmentms.pojo.User;
import com.example.studentapartmentms.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
@ResponseBody
@Slf4j
public class UserController {


    /**
     * 用户服务接口
     **/
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
     *
     * @param userIds 学生 ID 集合
     * @return 删除成功返回 true
     */
    @DeleteMapping
    public boolean deleteUser(
            HttpServletRequest request,
            @RequestBody List<Integer> userIds
    ) {
        // 检查当前用户是否是管理员
        Utils.isRole(userService, request, RoleEnum.ADMIN);

        log.info("DELETE_USER: " + userIds.toString());
        return userService.deleteUser(request, userIds);
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

}
