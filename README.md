# 苍穹外卖系统

## 项目简介

苍穹外卖系统是一个基于Spring Boot的外卖点餐管理系统，提供完整的后台管理功能和前端点餐功能。系统采用分层架构设计，具有良好的可扩展性和可维护性。

## 技术栈

- **后端框架**：Spring Boot 3.2.1
- **持久层**：MyBatis
- **数据库**：MySQL
- **认证**：JWT
- **文件存储**：阿里云OSS
- **支付**：微信支付API v3
- **API文档**：Knife4j (Swagger3)
- **分页**：PageHelper
- **连接池**：Druid
- **工具类**：Fastjson2, Commons Lang3, Lombok

## 项目结构

```
skyTakeout/
├── common/            # 公共模块
│   ├── src/main/java/com/myb/
│   │   ├── constant/    # 常量定义
│   │   ├── context/     # 上下文
│   │   ├── enumeration/ # 枚举类
│   │   ├── exception/   # 异常处理
│   │   ├── json/        # JSON处理
│   │   ├── properties/  # 配置属性
│   │   ├── result/      # 统一返回结果
│   │   └── utils/       # 工具类
│   └── pom.xml
├── pojo/              # 实体类模块
│   ├── src/main/java/com/myb/
│   │   ├── dto/        # 数据传输对象
│   │   ├── entity/     # 实体类
│   │   └── vo/         # 视图对象
│   └── pom.xml
├── server/            # 业务逻辑模块
│   ├── src/main/java/com/myb/
│   │   ├── annotation/  # 自定义注解
│   │   ├── aspect/      # 切面
│   │   ├── config/      # 配置
│   │   ├── controller/  # 控制器
│   │   ├── handler/     # 处理器
│   │   ├── interceptor/ # 拦截器
│   │   ├── mapper/      # 数据访问
│   │   ├── service/     # 业务逻辑
│   │   └── SkyApplication.java # 应用入口
│   ├── src/main/resources/
│   │   ├── mapper/      # XML映射文件
│   │   ├── application.yml      # 主配置文件
│   │   └── application-dev.yml  # 开发环境配置
│   └── pom.xml
├── .gitignore
├── pom.xml            # 父工程pom.xml
└── README.md          # 项目说明文档
```

## 核心功能模块

### 1. 员工管理
- 员工登录/退出
- 员工信息管理（增删改查）
- 员工状态管理（启用/禁用）
- 员工分页查询

### 2. 分类管理
- 分类信息管理

### 3. 菜品管理
- 菜品信息管理
- 菜品口味管理

### 4. 套餐管理
- 套餐信息管理
- 套餐菜品关联

### 5. 订单管理
- 订单信息管理
- 订单状态流转
- 订单支付

### 6. 用户管理
- 用户登录/注册
- 用户地址管理

### 7. 购物车管理
- 购物车添加/删除
- 购物车结算

## 快速开始

### 环境要求
- JDK 21+
- MySQL 8.0+
- Maven 3.6+

### 配置步骤
1. **克隆项目**
   ```bash
   git clone <项目地址>
   cd skyTakeout
   ```

2. **配置数据库**
   - 创建数据库 `sky_take_out`
   - 修改 `server/src/main/resources/application-dev.yml` 中的数据库配置

3. **构建项目**
   ```bash
   mvn clean install
   ```

4. **启动项目**
   ```bash
   mvn spring-boot:run -pl server
   ```

5. **访问API文档**
   - 浏览器访问：`http://localhost:8080/doc.html`

## 项目特点

1. **分层架构**：清晰的分层设计，便于代码维护和扩展
2. **统一响应**：统一的API响应格式，便于前端处理
3. **异常处理**：完善的异常处理机制
4. **JWT认证**：基于JWT的无状态认证
5. **AOP切面**：使用AOP实现日志记录、权限控制等横切关注点
6. **文件上传**：集成阿里云OSS实现文件存储
7. **微信支付**：集成微信支付API v3实现支付功能
8. **API文档**：使用Knife4j生成交互式API文档

## 数据库设计

系统包含以下主要表：
- `employee`：员工表
- `category`：分类表
- `dish`：菜品表
- `dish_flavor`：菜品口味表
- `setmeal`：套餐表
- `setmeal_dish`：套餐菜品关联表
- `orders`：订单表
- `order_detail`：订单详情表
- `user`：用户表
- `address_book`：地址簿表
- `shopping_cart`：购物车表

## 开发指南

### 代码规范
- 遵循Java编码规范
- 使用Lombok简化代码
- 方法和变量命名清晰明了
- 关键业务逻辑添加注释

### 提交规范
- 提交信息清晰描述改动内容
- 功能开发完成后提交测试
- 定期合并代码到主分支

## 部署说明

### 开发环境
- 直接运行 `SkyApplication.java` 或使用 `mvn spring-boot:run`

### 生产环境
1. **打包项目**
   ```bash
   mvn clean package -DskipTests
   ```

2. **运行jar包**
   ```bash
   java -jar server/target/skyTakeout-server-1.0-SNAPSHOT.jar
   ```

## 许可证

本项目采用MIT许可证。

## 联系方式

如有问题或建议，请联系项目维护者。
