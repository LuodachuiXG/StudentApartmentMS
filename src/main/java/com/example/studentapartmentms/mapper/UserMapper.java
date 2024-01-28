package com.example.studentapartmentms.mapper;

import com.example.studentapartmentms.pojo.RoleEnum;
import com.example.studentapartmentms.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户表 Mapper 接口
 */
@Mapper
public interface UserMapper {
    /**
     * 添加用户
     *
     * @param user 用户实体类
     */
    int addUser(@Param("user") User user);

    /**
     * 获取所有用户
     */
    List<User> allUser();

    /**
     * 修改用户
     *
     * @param user 用户实体类
     */
    int updateUser(@Param("user") User user);


    /**
     * 修改用户密码
     *
     * @param userId 用户 ID
     * @param password 新密码
     */
    int updateUserPassword(@Param("userId") Integer userId,
                           @Param("password") String password);

    /**
     * 根据用户 ID 获取用户
     *
     * @param userId 用户 ID
     */
    User userByUserId(@Param("userId") Integer userId);

    /**
     * 根据 ID 获取用户
     *
     * @param id 工号或学号
     */
    User userById(@Param("id") String id);

    /**
     * 根据身份获取用户
     *
     * @param role 用户身份
     */
    List<User> userByRole(@Param("role") RoleEnum role);

    /**
     * 关键词获取用户信息
     * 关键词：工号（学号）、姓名、电话
     *
     * @param key 查询的关键词
     */
    List<User> userByKey(@Param("key") String key);


    /**
     * 修改用户最后登录时间
     *
     * @param userId 用户 ID
     * @param time   最后登录时间
     */
    void updateLastLogin(@Param("userId") Integer userId,
                         @Param("time") LocalDateTime time);

    /**
     * 根据用户 ID 删除用户
     *
     * @param userIds 用户 ID 集合
     */
    int deleteByUserIds(@Param("userIds") List<Integer> userIds);
}
