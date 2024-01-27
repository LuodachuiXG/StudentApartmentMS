package com.example.studentapartmentms.controller;


import com.example.studentapartmentms.common.Utils;
import com.example.studentapartmentms.pojo.Pager;
import com.example.studentapartmentms.pojo.RoleEnum;
import com.example.studentapartmentms.pojo.User;
import com.example.studentapartmentms.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
@ResponseBody
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
        return userService.login(user.getId(), user.getPassword());
    }

    /**
     * 用户注册（此接口只能用于管理员注册）
     *
     * @param user 用户实体类
     * @return 注册成功返回用户信息，否则返回 null
     */
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
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
