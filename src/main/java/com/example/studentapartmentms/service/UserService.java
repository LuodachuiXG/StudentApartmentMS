package com.example.studentapartmentms.service;

import com.example.studentapartmentms.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDate;
import java.util.List;

/**
 * 用户服务接口
 */

public interface UserService {

    /**
     * 添加用户
     * @param user 用户实体类
     * @return 注册成功返回用户信息，否则返回 null
     */
    User addUser(User user);

    /**
     * 获取所有用户
     * @return 用户集合
     */
    List<User> allUser();

    /**
     * 根据 Token 获取用户
     * @param token 指定 Token
     * @return 用户存在则返回，不存在抛出 JWT 验证异常
     */
    User userByToken(String token);

    /**
     * 根据 ID 获取用户
     * @param id 工号或学号
     */
    User userById(String id);

    /**
     * 修改用户最后登录时间
     * @param userId 用户 ID
     */
    void updateLastLogin(Integer userId);

    /**
     * 用户登录
     * @param id 工号（学号）
     * @param password 密码
     * @return 登录成功返回 JSON 对象，封装了用户信息和 Token
     */
    ObjectNode login(String id, String password);

}
