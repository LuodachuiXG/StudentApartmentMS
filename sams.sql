CREATE DATABASE IF NOT EXISTS sams CHARACTER SET utf8mb4;

# 用户表
CREATE TABLE IF NOT EXISTS user
(
    userId    INT AUTO_INCREMENT COMMENT '用户 ID',
    name       VARCHAR(100) NOT NULL COMMENT '姓名',
    id         VARCHAR(20)  NOT NULL COMMENT '工号/学号',
    password   VARCHAR(100) NOT NULL COMMENT '密码',
    role       VARCHAR(10)  NOT NULL COMMENT '身份: STUDENT/ADMIN',
    phone      VARCHAR(20)  NOT NULL COMMENT '手机号',
    gender     VARCHAR(10)  NOT NULL COMMENT '性别: MALE/FEMALE',
    birth      DATE         NOT NULL COMMENT '年龄',
    lastLogin DATETIME     NULL,
    CONSTRAINT userId PRIMARY KEY (userId)
) COMMENT '用户表' COLLATE = utf8mb4_unicode_ci;

# 宿舍表
CREATE TABLE IF NOT EXISTS dormitory
(
    dormitoryId INT AUTO_INCREMENT COMMENT '公寓 ID',
    name         VARCHAR(50) NOT NULL COMMENT '公寓名',
    CONSTRAINT dormitoryId PRIMARY KEY (dormitoryId)
) COMMENT '宿舍表' COLLATE = utf8mb4_unicode_ci;



# 留言板表
CREATE TABLE IF NOT EXISTS message_board
(
    messageBoardId INT AUTO_INCREMENT COMMENT '留言 ID',
    dormitoryId     INT          NOT NULL COMMENT '宿舍 ID',
    userId          INT          NOT NULL COMMENT '用户 ID',
    content          VARCHAR(200) NOT NULL COMMENT '留言内容',
    createDate      DATETIME     NOT NULL COMMENT '发布时间',
    likeCount       INT          NOT NULL COMMENT '点赞数量',
    top              BOOLEAN      NULL COMMENT '置顶',
    CONSTRAINT messageBoardId PRIMARY KEY (messageBoardId),
    CONSTRAINT dormitoryId FOREIGN KEY (dormitoryId) REFERENCES dormitory (dormitoryId),
    CONSTRAINT userId FOREIGN KEY (userId) REFERENCES user (userId)
) COMMENT '留言板' COLLATE = utf8mb4_unicode_ci;

