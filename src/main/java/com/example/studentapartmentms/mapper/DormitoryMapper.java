package com.example.studentapartmentms.mapper;

import com.example.studentapartmentms.pojo.Dormitory;
import com.example.studentapartmentms.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 宿舍表 Mapper 接口
 */
@Mapper
public interface DormitoryMapper {

    /**
     * 获取所有宿舍
     * 注意：此 SQL 无法填充宿舍管理员字段 admins，需要二次查询手动填充
     * @return 宿舍集合
     */
    List<Dormitory> allDormitory();

    /**
     * 根据宿舍 ID 获取宿舍管理员
     * @param dormitoryId 宿舍 ID
     * @return 宿舍管理员用户集合
     */
    List<User> adminsByDormitoryId(@Param("dormitoryId") Integer dormitoryId);
}
