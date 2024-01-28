package com.example.studentapartmentms.service;

import com.example.studentapartmentms.pojo.Pager;
import com.example.studentapartmentms.pojo.RoleEnum;
import com.example.studentapartmentms.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.List;

/**
 * 用户服务接口
 */

public interface UserService {

    /**
     * 添加用户
     * @param user 用户实体类
     * @param role 用户身份
     * @return 注册成功返回用户信息，否则返回 null
     */
    User addUser(User user, RoleEnum role);


    /**
     * 删除用户
     * 管理员只可以删除学生。删除管理员需要自己注销。
     * @param userIds 用户 ID 集合
     * @return 删除成功返回 true
     */
    Boolean deleteUser(HttpServletRequest request, List<Integer> userIds);

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
     * 分页和关键词获取用户信息
     * 只有管理员可以获取所有用户信息
     * 关键词：工号（学号）、姓名、电话
     *
     * @param key 查询的关键词
     * @param page 当前页数
     * @param size 每页大小
     */
    Pager<User> userByKeyAndPage(String key, Integer page, Integer size);

    /**
     * 根据 ID 获取用户
     * @param id 工号或学号
     */
    User userById(String id);

    /**
     * 分页获取用户
     * @param page 当前页
     * @param size 每页数据大小
     */
    Pager<User> userByPage(Integer page, Integer size);

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
