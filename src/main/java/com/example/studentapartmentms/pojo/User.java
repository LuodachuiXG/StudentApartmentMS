package com.example.studentapartmentms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@TableName("user")
public class User implements Serializable {
    /** 用户 ID **/
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /** 姓名 **/
    private String name;

    /** 工号或学号 **/
    private String id;

    /** 密码 **/
    private String password;

    /** 用户角色 **/
    private RoleEnum role;

    /** 手机号 **/
    private String phone;

    /** 性别 **/
    private GenderEnum gender;

    /** 出生日期 **/
    private LocalDate birth;

    /** 最后一次登录 **/
    @TableField("last_login")
    private LocalDateTime lastLogin;
}
