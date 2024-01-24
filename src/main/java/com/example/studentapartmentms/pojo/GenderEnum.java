package com.example.studentapartmentms.pojo;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * 用户性别枚举类
 */
public enum GenderEnum implements IEnum<String> {
    MALE("male"),
    FEMALE("female");

    private final String gender;
    GenderEnum(String gender) {
        this.gender = gender;
    }

    @Override
    public String getValue() {
        return this.gender;
    }
}
