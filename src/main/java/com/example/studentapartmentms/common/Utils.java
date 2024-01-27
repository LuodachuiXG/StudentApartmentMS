package com.example.studentapartmentms.common;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.studentapartmentms.pojo.RoleEnum;
import com.example.studentapartmentms.pojo.User;
import com.example.studentapartmentms.service.UserService;
import com.example.studentapartmentms.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 工具类
 */
public class Utils {
    /**
     * 判断文本是否是数字
     *
     * @param str 要判断的文本
     * @return 数字返回 true，反之 false
     */
    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据 Token 判断绑定的用户是否是指定的角色
     * 如果用户角色不符，抛出 JWT 验证异常
     *
     * @param request Request 请求，用于获取 Token
     * @param role    用户角色
     */
    public static void isRole(
            UserService userService,
            HttpServletRequest request,
            RoleEnum role
    ) {
        // 根据 Token 获取用户
        String token = request.getHeader("token");
        isRole(userService, token, role);
    }

    /**
     * 根据 Token 判断绑定的用户是否是指定的角色
     * 如果用户角色不符，抛出 JWT 验证异常
     *
     * @param token Token
     * @param role  用户角色
     */
    public static void isRole(
            UserService userService,
            String token,
            RoleEnum role
    ) {
        // 根据 Token 获取用户
        User user = userService.userByToken(token);
        // 检查当前用户是否指定角色
        if (user.getRole() != role) {
            throw new JWTVerificationException("无权访问受保护资源");
        }
    }
}
