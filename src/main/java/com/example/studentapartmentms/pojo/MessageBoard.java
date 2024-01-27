package com.example.studentapartmentms.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageBoard {
    /** 留言板 ID **/
    private Integer messageBoardId;

    /** 用户 ID **/
    private Integer userId;

    /** 留言内容 **/
    private String content;

    /** 留言发布时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createDate;

    /** 点赞数 **/
    private Integer likeCount;

    /** 置顶 **/
    private Boolean top;

    /** 用户实体类 **/
    private User user;
}
