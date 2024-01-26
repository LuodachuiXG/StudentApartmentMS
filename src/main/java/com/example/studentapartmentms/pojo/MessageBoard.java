package com.example.studentapartmentms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("message_board")
public class MessageBoard {
    /** 留言板 ID **/
    @TableId(value = "message_board_id", type = IdType.AUTO)
    private Integer messageBoardId;

    /** 用户 ID **/
    @TableField(value = "user_id")
    private Integer userId;

    /** 留言内容 **/
    private String content;

    /** 留言发布时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "create_date")
    private LocalDateTime createDate;

    /** 点赞数 **/
    @TableField(value = "like_count")
    private Integer likeCount;

    /** 置顶 **/
    private Boolean top;

    /** 用户实体类 **/
    @TableField(exist = false)
    private User user;
}
