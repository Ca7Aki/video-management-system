-- 1. 重建数据库
DROP DATABASE IF EXISTS video_db;
CREATE DATABASE video_db DEFAULT CHARACTER SET utf8mb4;
USE video_db;

-- 2. 创建表（严格按依赖顺序）
CREATE TABLE sys_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(50)
);

CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(50),
    enabled TINYINT(1) DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sys_user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES sys_user(id),
    FOREIGN KEY (role_id) REFERENCES sys_role(id)
);

CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL UNIQUE,
    description VARCHAR(100),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE video (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    cover_url VARCHAR(200),
    category_id BIGINT,
    play_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES category(id)
);

-- 3. 插入角色
INSERT INTO sys_role (name, description) VALUES ('ROLE_ADMIN', '管理员');
INSERT INTO sys_role (name, description) VALUES ('ROLE_USER', '普通用户');

-- 4. 插入用户（密码为 123456 的 BCrypt 密文）
--    密文来源：通过 BCryptPasswordEncoder 生成，已验证可用
INSERT INTO sys_user (username, password, email, enabled) VALUES
('admin', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTkC76kq', 'admin@test.com', 1),
('user', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTkC76kq', 'user@test.com', 1);

-- 5. 建立用户-角色关联（直接使用已知的自增ID：角色1,2 用户1,2）
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1), (2, 2);

-- 6. 插入分类（自增ID 1 ~ 5）
INSERT INTO category (name, description) VALUES
('电影', '包含各类电影作品'),
('电视剧', '电视剧与网剧资源'),
('动漫', '动画、漫画及相关视频'),
('纪录片', '纪实类视频内容'),
('综艺', '娱乐综艺节目');

-- 7. 插入视频（category_id 对应上面的 1~5）
INSERT INTO video (title, cover_url, category_id, play_count, create_time) VALUES
('流浪地球2', 'https://img.example.com/cover1.jpg', 1, 125000, '2025-01-22 09:00:00'),
('满江红', 'https://img.example.com/cover2.jpg', 1, 98000, '2025-02-10 10:30:00'),
('狂飙', 'https://img.example.com/cover3.jpg', 2, 250000, '2025-01-15 20:00:00'),
('三体', 'https://img.example.com/cover4.jpg', 2, 178000, '2025-03-01 12:00:00'),
('斗罗大陆', 'https://img.example.com/cover5.jpg', 3, 320000, '2025-02-28 18:00:00'),
('间谍过家家', 'https://img.example.com/cover6.jpg', 3, 210000, '2025-04-05 15:20:00'),
('蓝色星球', 'https://img.example.com/cover7.jpg', 4, 67000, '2025-01-05 08:00:00'),
('舌尖上的中国', 'https://img.example.com/cover8.jpg', 4, 89000, '2025-03-15 19:30:00'),
('王牌对王牌', 'https://img.example.com/cover9.jpg', 5, 150000, '2025-02-14 22:00:00'),
('奔跑吧兄弟', 'https://img.example.com/cover10.jpg', 5, 190000, '2025-04-10 21:00:00'),
('独行月球', 'https://img.example.com/cover11.jpg', 1, 87000, '2025-05-01 00:00:00'),
('庆余年', 'https://img.example.com/cover12.jpg', 2, 140000, '2025-06-01 12:00:00'),
('完美世界', 'https://img.example.com/cover13.jpg', 3, 95000, '2025-06-10 18:00:00'),
('地球脉动', 'https://img.example.com/cover14.jpg', 4, 56000, '2025-06-20 08:00:00'),
('脱口秀大会', 'https://img.example.com/cover15.jpg', 5, 110000, '2025-07-01 20:00:00');

-- 验证数据
SELECT '角色表' AS 表名, COUNT(*) AS 记录数 FROM sys_role
UNION ALL
SELECT '用户表', COUNT(*) FROM sys_user
UNION ALL
SELECT '用户角色关联表', COUNT(*) FROM sys_user_role
UNION ALL
SELECT '分类表', COUNT(*) FROM category
UNION ALL
SELECT '视频表', COUNT(*) FROM video;
