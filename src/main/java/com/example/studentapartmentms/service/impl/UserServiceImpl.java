package com.example.studentapartmentms.service.impl;

import com.example.studentapartmentms.mapper.UserMapper;
import com.example.studentapartmentms.pojo.GenderEnum;
import com.example.studentapartmentms.pojo.RoleEnum;
import com.example.studentapartmentms.pojo.User;
import com.example.studentapartmentms.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 用户服务接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

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
    @Override
    public boolean addUser(String name, String id, String password,
                        String role, String phone,
                        String gender, LocalDate birth
    ) {
        User user = new User();
        user.setName(name);
        user.setId(id);
        user.setPassword(password);

        if (role.equals("student")) {
            user.setRole(RoleEnum.STUDENT);
        } else if (role.equals("admin")) {
            user.setRole(RoleEnum.ADMIN);
        } else {
            throw new RuntimeException("role 只能为 student 或 admin");
        }

        user.setPhone(phone);

        if (gender.equals("male")) {
            user.setGender(GenderEnum.MALE);
        } else if (gender.equals("female")) {
            user.setGender(GenderEnum.FEMALE);
        } else {
            throw new RuntimeException("gender 只能为 male 或 female");
        }

        user.setBirth(birth);
        return userMapper.insert(user) == 1;
    }

    /**
     * 获取所有用户
     * @return 用户集合
     */
    @Override
    public List<User> allUser() {
        throw new RuntimeException("测试一下");
//        return userMapper.selectList(null);
    }
}
