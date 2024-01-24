package com.example.studentapartmentms.service;

import com.example.studentapartmentms.pojo.User;

import java.time.LocalDate;
import java.util.List;

/**
 * 用户服务接口
 */

public interface UserService {

    /**
     * 添加用户
     * @param name 姓名
     * @param id 工号或学号
     * @param password 密码
     * @param role 角色
     * @param phone 手机
     * @param gender 性别
     * @param birth 生日
     */
    boolean addUser(String name, String id, String password,
                 String role, String phone,
                 String gender, LocalDate birth);

    /**
     * 获取所有用户
     * @return 用户集合
     */
    List<User> allUser();

}
