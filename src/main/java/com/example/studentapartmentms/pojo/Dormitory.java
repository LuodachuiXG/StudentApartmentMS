package com.example.studentapartmentms.pojo;

import lombok.Data;

/**
 * 宿舍实体类
 */
@Data
public class Dormitory {
    /** 宿舍 ID **/
    private Integer dormitoryId;

    /** 宿舍名 */
    private String name;
}
