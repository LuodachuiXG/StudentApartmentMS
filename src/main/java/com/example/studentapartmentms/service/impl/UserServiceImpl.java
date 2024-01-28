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
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
     * @param user 用户实体类
     * @param role 用户身份
     * @return 注册成功返回用户信息，否则返回 null
     */
    @Override
    public User addUser(User user, RoleEnum role) {
        user.setRole(role);
        if (user.getGender() == null) {
            throw new MyException("gender 只能为 MALE 或 FEMALE");
        }

        // 首先检查工号（学号）是否已经存在
        User u = userById(user.getId());
        if (u != null) {
            // 工号（学号）已经存在
            throw new MyException("工号（学号）已经存在");
        }

        // 验证手机号正确性
        if (!Utils.isNumber(user.getPhone(), 11)) {
            throw new MyException("手机号格式有误");
        }

        // 学生默认密码为手机号后六位
        if (role == RoleEnum.STUDENT) {
            user.setPassword(user.getPhone().substring(5, 11));
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
     * 删除用户
     * 管理员只可以删除学生。删除管理员需要自己注销。
     * @param requestUserId 调用者用户 ID
     * @param userIds 用户 ID 集合
     * @return 删除成功返回 true
     */
    @Override
    public Boolean deleteUser(Integer requestUserId, List<Integer> userIds) {
        // 获取学生用户
        List<User> students = userMapper.userByRole(RoleEnum.STUDENT);
        // 学生用户 ID
        List<Integer> studentIds = new ArrayList<>();
        students.forEach((student) -> studentIds.add(student.getUserId()));

        // 遍历要删除的用户 ID，不能包含管理员，除非是当前调用的用户本人
        userIds.forEach((userId) -> {
            // 当前要删除的用户 ID 既不是学生的，也不是当前调用者的
            if (!studentIds.contains(userId) && !userId.equals(requestUserId)) {
                throw new MyException("不能删除其他管理员");
            }
        });

        int result = userMapper.deleteByUserIds(userIds);
        return result > 0;
    }

    /**
     * 修改用户
     * @param requestUserId 请求调用者 ID
     * @param user 用户实体类
     * @return 修改成功返回 true
     */
    @Override
    public Boolean updateUser(Integer requestUserId, User user) {
        // 根据用户 ID 获取请求用户实体类
        User requestUser = userByUserId(requestUserId);

        // 根据用户 ID 获取将要修改的用户实体类
        User willUpdateUser = userByUserId(user.getUserId());

        if (willUpdateUser == null) {
            // 将要修改的用户为 null
            throw new MyException("待修改的用户不存在");
        }

        // 请求者是学生，但是修改的用户不是自己
        if (requestUser.getRole() == RoleEnum.STUDENT && (!requestUserId.equals(user.getUserId()))) {
            throw new MyException("学生无权修改其他用户");
        }

        // 请求者是管理员，将要修改的用户也是管理员，但是不是自己
        if (requestUser.getRole() == RoleEnum.ADMIN &&
                (willUpdateUser.getRole() == RoleEnum.ADMIN &&
                        !requestUser.getUserId().equals(willUpdateUser.getUserId()))) {
            throw new MyException("管理员无权修改其他管理员");
        }

        // 验证手机号正确性
        if (!Utils.isNumber(user.getPhone(), 11)) {
            throw new MyException("手机号格式有误");
        }

        String password = user.getPassword();
        // 查看密码是否为空
        if (password != null && !password.isBlank()) {
            // 密码不为空，先修改密码
            userMapper.updateUserPassword(user.getUserId(), MD5Utils.getMd5Hash(password));
        }

        return userMapper.updateUser(user) > 0;
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
     * 修改用户密码
     * 此接口只能修改密码的用户本人调用
     * @param requestUserId 请求者用户 ID
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return 修改成功返回 true
     */
    @Override
    public Boolean updateUserPassword(Integer requestUserId, String oldPwd, String newPwd) {
        // 获取请求用户
        User user = userByUserId(requestUserId);
        // 验证旧密码是否正确
        if (!user.getPassword().equals(MD5Utils.getMd5Hash(oldPwd))) {
            // 旧密码错误
            throw new MyException("旧密码错误");
        }

        return userMapper.updateUserPassword(requestUserId, MD5Utils.getMd5Hash(newPwd)) > 0;
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
        // 根据 Token 获取用户 ID
        int userId = Utils.getUserIdByToken(token);

        // 根据 userId 获取用户
        User user = userByUserId(userId);
        if (user == null) {
            // userId 有问题
            throw new JWTVerificationException("Token 异常");
        }
        return user;
    }

    /**
     * 根据用户 ID 获取用户
     *
     * @param userId 用户 ID
     * @return 用户或 null
     */
    @Override
    public User userByUserId(Integer userId) {
        return userMapper.userByUserId(userId);
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
        return Utils.getPager(list, page, size);
    }

    /**
     * 分页和关键词获取用户信息
     * 只有管理员可以获取所有用户信息
     * 关键词：工号（学号）、姓名、电话
     *
     * @param key 查询的关键词
     * @param page 当前页数
     * @param size 每页大小
     */
    @Override
    public Pager<User> userByKeyAndPage(String key, Integer page, Integer size) {
        // 开始分页查询，执行此代码之后的 SQL 会被自动加上分页的代码
        PageHelper.startPage(page, size);
        // 此处的获取所有用户的 SQL 已经被加上了分页代码
        List<User> list = userMapper.userByKey(key);
        return Utils.getPager(list, page, size);
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
