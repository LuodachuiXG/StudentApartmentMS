CREATE DATABASE IF NOT EXISTS sams CHARACTER SET utf8mb4;

# 用户表
CREATE TABLE IF NOT EXISTS user
(
    user_id    INT AUTO_INCREMENT COMMENT '用户 ID',
    name       VARCHAR(100) NOT NULL COMMENT '姓名',
    id         VARCHAR(20)  NOT NULL COMMENT '工号/学号',
    password   VARCHAR(100) NOT NULL COMMENT '密码',
    role       VARCHAR(10)  NOT NULL COMMENT '身份: student/admin',
    phone      VARCHAR(20)  NOT NULL COMMENT '手机号',
    gender     VARCHAR(10)  NOT NULL COMMENT '性别: male/female',
    birth      DATE         NOT NULL COMMENT '年龄',
    last_login DATETIME     NULL,
    CONSTRAINT user_id PRIMARY KEY (user_id)
) COMMENT '用户表' COLLATE = utf8mb4_unicode_ci;

# 宿舍表
CREATE TABLE IF NOT EXISTS dormitory
(
    dormitory_id INT AUTO_INCREMENT COMMENT '公寓 ID',
    name         VARCHAR(50) NOT NULL COMMENT '公寓名',
    CONSTRAINT dormitory_id PRIMARY KEY (dormitory_id)
) COMMENT '宿舍表' COLLATE = utf8mb4_unicode_ci;



# 留言板表
CREATE TABLE IF NOT EXISTS message_board
(
    message_board_id INT AUTO_INCREMENT COMMENT '留言 ID',
    dormitory_id     INT          NOT NULL COMMENT '宿舍 ID',
    user_id          INT          NOT NULL COMMENT '用户 ID',
    content          VARCHAR(200) NOT NULL COMMENT '留言内容',
    create_date      DATETIME     NOT NULL COMMENT '发布时间',
    like_count       INT          NOT NULL COMMENT '点赞数量',
    top              BOOLEAN      NULL COMMENT '置顶',
    CONSTRAINT message_board_id PRIMARY KEY (message_board_id),
    CONSTRAINT dormitory_id FOREIGN KEY (dormitory_id) REFERENCES dormitory (dormitory_id),
    CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES user (user_id)
) COMMENT '留言板' COLLATE = utf8mb4_unicode_ci;

