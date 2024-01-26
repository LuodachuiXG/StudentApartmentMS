package com.example.studentapartmentms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 宿舍实体类
 */
@Data
@TableName("dormitory")
public class Dormitory {
    /** 宿舍 ID **/
    @TableId(value = "dormitory_id", type = IdType.AUTO)
    private Integer dormitoryId;

    /** 宿舍名 */
    private String name;
}
