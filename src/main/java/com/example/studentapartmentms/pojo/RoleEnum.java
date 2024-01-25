package com.example.studentapartmentms.pojo;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 用户角色枚举类
 */
public enum RoleEnum implements IEnum<String> {
    STUDENT("student"),

    ADMIN("admin");

    /** 使用注解将 STUDENT 序列化为 student，而不是 STUDENT **/
    @JsonSerialize(using = ToStringSerializer.class)
    private final String role;
    RoleEnum(String role) {
        this.role = role;
    }

    /** JsonValue 注解指定此方法返回的值在序列化时被使用 **/
    @JsonValue
    @Override
    public String getValue() {
        return this.role;
    }
}
