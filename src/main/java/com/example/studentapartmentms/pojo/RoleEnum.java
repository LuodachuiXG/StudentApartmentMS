package com.example.studentapartmentms.pojo;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 用户角色枚举类
 */
public enum RoleEnum {
    STUDENT,
    ADMIN
}
