package com.example.studentapartmentms.controller;


import com.example.studentapartmentms.common.MyException;
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


    /** 用户服务接口 **/
    @Resource
    private UserService userService;

    /**
     * 用户登录
     * @param user 用户实体类
     * @return 登录成功返回用户信息和 Token
     */
    @PostMapping("/login")
    public ObjectNode login(@RequestBody User user) {
        return userService.login(user.getId(), user.getPassword());
    }

    /**
     * 用户注册（此接口只能用于管理员注册）
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
        // 根据 Token 获取用户
        String token = request.getHeader("token");
        User user = userService.userByToken(token);

        // 检查当前用户是否是管理员
        if (user.getRole() == RoleEnum.STUDENT) {
            // 当前用户身份是学生
            // 学生无权获取所有用户信息
            throw new MyException("学生无权查看所有用户");
        }
        // 返回所有用户信息
        return userService.allUser();
    }

}
