package com.example.studentapartmentms.service.impl;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.studentapartmentms.common.JWTUtils;
import com.example.studentapartmentms.common.MD5Utils;
import com.example.studentapartmentms.common.MyException;
import com.example.studentapartmentms.common.Utils;
import com.example.studentapartmentms.mapper.UserMapper;
import com.example.studentapartmentms.pojo.RoleEnum;
import com.example.studentapartmentms.pojo.User;
import com.example.studentapartmentms.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 用户服务接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 添加用户
     * @param user 用户实体类
     * @return 注册成功返回用户信息，否则返回 null
     */
    @Override
    public User addUser(User user
    ) {
        user.setRole(RoleEnum.ADMIN);
        if (user.getGender() == null) {
            throw new MyException("gender 只能为 male 或 female");
        }

        // 首先检查工号（学号）是否已经存在
        User u = userById(user.getId());
        if (u != null) {
            // 工号（学号）已经存在
            throw new MyException("工号（学号）已经存在");
        }

        // 加入用户
        int result = userMapper.insert(user);
        if (result == 1) {
            // 注册成功，返回用户信息，但是抹去密码
            user.setPassword(null);
            return user;
        } else {
            return null;
        }
    }

    /**
     * 获取所有用户
     * @return 用户集合
     */
    @Override
    public List<User> allUser() {
        List<User> users = userMapper.selectList(null);
        // 清除用户敏感信息
        users.forEach((user -> user.setPassword(null)));
        return users;
    }

    /**
     * 根据 Token 获取用户
     * @param token 指定 Token
     * @return 用户存在则返回，不存在抛出 JWT 验证异常
     */
    @Override
    public User userByToken(String token) {
        // 获取当前 Token Claims
        Map<String, String> claims = JWTUtils.getClaims(token);
        // 获取当前 Token 绑定的用户 ID
        String userId = claims.get("userId");
        if (userId == null || !Utils.isNumber(userId)) {
            // userId 有问题
            throw new JWTVerificationException("Token 异常");
        }

        // 根据 userId 获取用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            // userId 有问题
            throw new JWTVerificationException("Token 异常");
        }
        return user;
    }

    /**
     * 根据 ID 获取用户
     * @param id 工号或学号
     */
    @Override
    public User userById(String id) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getId, id);
        return userMapper.selectOne(qw);
    }

    /**
     * 修改用户最后登录时间
     * @param userId 用户 ID
     */
    @Override
    public void updateLastLogin(Integer userId) {
        LambdaUpdateWrapper<User> uw = new LambdaUpdateWrapper<>();
        uw.eq(User::getUserId, userId)
                .set(User::getLastLogin, LocalDateTime.now());
        userMapper.update(uw);
    }

    /**
     * 用户登录
     * @param id 工号（学号）
     * @param password 密码
     * @return 登录成功返回 JSON 对象，封装了用户信息和 Token
     */
    @Override
    public ObjectNode login(String id, String password) {
        // 根据用户 ID 获取用户
        User user = userById(id);
        if (user == null) {
            // 用户不存在，或账号密码错误
            throw new MyException("工号（学号）或密码错误");
        }

        // 用户存在，验证密码
        if (!user.getPassword().equals(MD5Utils.getMd5Hash(password))) {
            // 密码错误
            throw new MyException("工号（学号）或密码错误");
        }

        // 密码正确，生成与用户 ID 绑定的 Token
        String token = JWTUtils.generateToken(Map.of("userId", user.getUserId().toString()));

        // 修改用户最后登录时间
        updateLastLogin(user.getUserId());

        // 将 user 实体类转为 JSON 对象
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.valueToTree(user);
        // 去处用户敏感数据
        json.remove("password");
        json.put("token", token);
        return json;
    }
}
