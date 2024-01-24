package com.example.studentapartmentms.controller;


import com.alibaba.fastjson2.JSONObject;
import com.example.studentapartmentms.common.MyException;
import com.example.studentapartmentms.pojo.RoleEnum;
import com.example.studentapartmentms.pojo.User;
import com.example.studentapartmentms.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.LocalDate;
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
     * @param id 工号或学号
     * @param password 密码
     * @return 登录成功返回用户信息和 Token
     */
    @PostMapping("/login")
    public JSONObject login(String id, String password) {
        return userService.login(id, password);
    }

    /**
     * 用户注册（此接口只能用于管理员注册）
     * @param name 姓名
     * @param id 工号
     * @param password 密码
     * @param phone 手机号
     * @param gender 性别
     * @param birth 生日
     * @return 注册成功返回用户信息，否则返回 null
     */
    @PostMapping
    public User addUser(String name, String id,
                        String password, String phone,
                        String gender, LocalDate birth
    ) {
        return userService.addUser(name, id, password, RoleEnum.ADMIN, phone, gender, birth);
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
