package com.example.studentapartmentms.pojo;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 用户性别枚举类
 */
public enum GenderEnum  {
    MALE,
    FEMALE
}
