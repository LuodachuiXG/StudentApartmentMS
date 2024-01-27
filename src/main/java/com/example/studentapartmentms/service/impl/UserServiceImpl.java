package com.example.studentapartmentms.service.impl;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.studentapartmentms.common.JWTUtils;
import com.example.studentapartmentms.common.MD5Utils;
import com.example.studentapartmentms.expcetion.MyException;
import com.example.studentapartmentms.common.Utils;
import com.example.studentapartmentms.mapper.UserMapper;
import com.example.studentapartmentms.pojo.Pager;
import com.example.studentapartmentms.pojo.RoleEnum;
import com.example.studentapartmentms.pojo.User;
import com.example.studentapartmentms.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 用户服务接口实现类
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 添加用户
     *
     * @param user 用户实体类
     * @return 注册成功返回用户信息，否则返回 null
     */
    @Override
    public User addUser(User user
    ) {
        user.setRole(RoleEnum.ADMIN);
        if (user.getGender() == null) {
            throw new MyException("gender 只能为 MALE 或 FEMALE");
        }

        // 首先检查工号（学号）是否已经存在
        User u = userById(user.getId());
        if (u != null) {
            // 工号（学号）已经存在
            throw new MyException("工号（学号）已经存在");
        }

        user.setPassword(MD5Utils.getMd5Hash(user.getPassword()));
        // 加入用户
        int result = userMapper.addUser(user);
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
     *
     * @return 用户集合
     */
    @Override
    public List<User> allUser() {
        return userMapper.allUser();
    }

    /**
     * 根据 Token 获取用户
     *
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
        User user = userMapper.userByUserId(Integer.valueOf(userId));
        if (user == null) {
            // userId 有问题
            throw new JWTVerificationException("Token 异常");
        }
        return user;
    }

    /**
     * 根据 ID 获取用户
     *
     * @param id 工号或学号
     */
    @Override
    public User userById(String id) {
        return userMapper.userById(id);
    }

    /**
     * 分页获取用户
     *
     * @param page 当前页
     * @param size 每页数据大小
     */
    @Override
    public Pager<User> userByPage(Integer page, Integer size) {
        // 开始分页查询，执行此代码之后的 SQL 会被自动加上分页的代码
        PageHelper.startPage(page, size);
        // 此处的获取所有用户的 SQL 已经被加上了分页代码
        List<User> list = allUser();
        PageInfo<User> pageInfo = new PageInfo<>(list);
        // 获取用户总数
        long totalUser = pageInfo.getTotal();
        // 获取总页数
        int totalPage = pageInfo.getPages();
        Pager<User> pager = new Pager<>();
        pager.setPage(page);
        pager.setSize(size);
        pager.setData(pageInfo.getList());
        pager.setTotalData(totalUser);
        pager.setTotalPage(totalPage);
        return pager;
    }

    /**
     * 修改用户最后登录时间
     *
     * @param userId 用户 ID
     */
    @Override
    public void updateLastLogin(Integer userId) {
        userMapper.updateLastLogin(userId, LocalDateTime.now());
    }

    /**
     * 用户登录
     *
     * @param id       工号（学号）
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
