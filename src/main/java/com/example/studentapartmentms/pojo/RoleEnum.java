package com.example.studentapartmentms.pojo;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * 用户角色枚举类
 */
public enum RoleEnum implements IEnum<String> {
    STUDENT("student"),
    ADMIN("admin");

    private final String role;
    RoleEnum(String role) {
        this.role = role;
    }

    @Override
    public String getValue() {
        return this.role;
    }
}
