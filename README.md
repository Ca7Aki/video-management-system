# 视频分类查询系统

基于 **Spring Boot + Spring Security + Spring Data JPA + Thymeleaf + MySQL + Redis** 的视频分类查询管理系统，实现用户端视频分类查询与管理员端数据管理。

## 功能模块

### 用户端（角色 USER / ADMIN）

| 路径 | 说明 |
| --- | --- |
| `/user/video/list` | 视频列表：分页浏览、按分类筛选、关键字搜索 |
| `/user/category/list` | 分类列表（Redis 缓存） |

### 管理员端（角色 ADMIN）

| 路径 | 说明 |
| --- | --- |
| `/admin/video/list` | 视频管理列表 |
| `/admin/video/add`、`/edit/{id}`、`/save`、`/delete/{id}` | 视频新增 / 编辑 / 保存 / 删除 |
| `/admin/category/list`、`/add`、`/edit/{id}`、`/save`、`/delete/{id}` | 分类管理全套操作 |

### 公共

- `/login`：表单登录页，登录成功后跳转 `/index`
- `/index`：首页

## 技术栈

| 分类 | 技术选型 |
| --- | --- |
| 基础框架 | Spring Boot 2.7.18 |
| 语言 | Java 11 |
| 安全框架 | Spring Security（内存认证 + BCrypt 加密 + 角色授权） |
| 持久层 | Spring Data JPA + MySQL 8（驱动 8.0.33） |
| 视图层 | Thymeleaf |
| 缓存 | Spring Cache + Redis |
| 构建 | Maven |

## 目录结构

```
.
├── video_db.sql                   # 数据库初始化脚本（建库建表 + 测试数据）
├── 项目报告.doc                    # 课程项目报告
└── 视频管理系统
    ├── pom.xml
    ├── mvnw / mvnw.cmd                # Maven 包装器
    └── src
        ├── main
        │   ├── java/com/example/video
        │   │   ├── VideoApplication.java   # 启动类
        │   │   ├── config/                 # Spring Security、Redis 配置
        │   │   ├── controller/             # 用户端 / 管理端控制器
        │   │   ├── entity/                 # 实体：Video、Category、SysUser、SysRole
        │   │   ├── repository/             # JPA 仓储
        │   │   ├── service/                # 业务层
        │   │   └── util/                   # 分页工具 PageUtils
        │   └── resources
        │       ├── application.yml         # 主配置（激活 dev 环境）
        │       ├── application-dev.yml     # 开发环境配置（数据库 / Redis 连接）
        │       └── templates/              # Thymeleaf 页面模板
        └── test/java/com/example/video      # 测试类
```

## 快速开始

### 1. 环境要求

- JDK 11+
- Maven 3.6+
- MySQL 8.x
- Redis（可选，用于分类缓存）

### 2. 初始化数据库

用 MySQL 客户端执行仓库根目录的 `video_db.sql`，脚本会自动完成：

- 建库 `video_db`（utf8mb4）
- 建表 `sys_role`、`sys_user`、`sys_user_role`、`category`、`video`
- 写入 2 个角色、2 个用户、5 个分类、15 条视频测试数据

### 3. 修改开发环境配置

编辑 `src/main/resources/application-dev.yml`，把其中的 MySQL 与 Redis 连接信息改成你自己的（本项目默认使用 `spring.profiles.active=dev`）。

### 4. 启动

```bash
mvn spring-boot:run
```

或使用 IDEA 打开项目运行 `VideoApplication` 主类。

启动后访问 <http://localhost:8080>（若 `application-dev.yml` 中配置了其他端口，以配置为准）。

### 5. 演示账号

| 账号 | 密码 | 角色 |
| --- | --- | --- |
| `admin` | `123456` | 管理员（可进入 /admin/**） |
| `user` | `123456` | 普通用户 |

> 账号密码为课程演示用固定值，密码在数据库中以 BCrypt 密文存储。

## 说明

- `SecurityConfig` 中的用户认证目前采用**内存认证**（`inMemoryAuthentication`），JDBC 认证的实现代码已在该类中注释保留，便于后续切换为基于 `sys_user` 表的数据库认证。
- `application-dev.yml` 属于个人开发环境配置，其中的数据库与 Redis 凭据为本地调试用途，使用者请自行按实际情况修改。
