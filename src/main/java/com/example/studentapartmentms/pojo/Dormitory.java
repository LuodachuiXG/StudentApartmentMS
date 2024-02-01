package com.example.studentapartmentms.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 宿舍实体类
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dormitory implements Serializable {
    /** 宿舍 ID **/
    private Integer dormitoryId;

    /** 宿舍名 */
    private String name;

    /** 宿舍总床位 **/
    private Integer totalBeds;

    /** 宿舍已入住人数 **/
    private Integer headCount;

    /** 宿舍管理员，只包含 name 和 id 字段**/
    private List<User> admins;
}
