package com.example.studentapartmentms.pojo;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 用户性别枚举类
 */
public enum GenderEnum implements IEnum<String> {

    MALE("male"),

    FEMALE("female");

    /** 使用注解将 MALE 序列化为 male，而不是 MALE **/
    @JsonSerialize(using = ToStringSerializer.class)
    private final String gender;

    GenderEnum(String gender) {
        this.gender = gender;
    }

    /** JsonValue 注解指定此方法返回的值在序列化时被使用 **/
    @JsonValue
    @Override
    public String getValue() {
        return this.gender;
    }
}
