# POM 文件修改记录

## 父工程（skyTakeout/pom.xml）
- 移除 dependencyManagement 中无版本的 Spring Boot 依赖项：
  - org.springframework.boot:spring-boot-starter
  - org.springframework.boot:spring-boot-starter-web
  - org.springframework.boot:spring-boot-starter-test（scope=test）
- 目的：避免子模块出现 “dependencies.dependency.version 缺失” 的构建错误，并统一由 Spring Boot Parent 管理版本。
- 文件链接：[pom.xml](file:///d:/Code/Sky-Takeout-System/skyTakeout/pom.xml)

## common 模块（common/pom.xml）
- com.alibaba:fastjson → com.alibaba.fastjson2:fastjson2
- commons-lang:commons-lang → org.apache.commons:commons-lang3
- javax.xml.bind:jaxb-api → jakarta.xml.bind:jakarta.xml.bind-api
- 新增测试依赖：org.springframework.boot:spring-boot-starter-test（scope=test）
- 文件链接：[common/pom.xml](file:///d:/Code/Sky-Takeout-System/skyTakeout/common/pom.xml)

## pojo 模块（pojo/pom.xml）
- com.github.xiaoymin:knife4j-spring-boot-starter → com.github.xiaoymin:knife4j-openapi3-jakarta-spring-boot-starter
- 新增测试依赖：org.springframework.boot:spring-boot-starter-test（scope=test）
- 合并重复的 <dependencies> 节点，保持结构规范
- 文件链接：[pojo/pom.xml](file:///d:/Code/Sky-Takeout-System/skyTakeout/pojo/pom.xml)

## server 模块（server/pom.xml）
- 内部模块坐标修正：
  - com.sky:sky-common → com.myb:common
  - com.sky:sky-pojo → com.myb:pojo
- 依赖与生态统一：
  - com.alibaba:druid-spring-boot-starter → com.alibaba:druid-spring-boot-3-starter
  - com.alibaba:fastjson → com.alibaba.fastjson2:fastjson2
  - javax.xml.bind:jaxb-api → jakarta.xml.bind:jakarta.xml.bind-api
  - com.github.xiaoymin:knife4j-spring-boot-starter → knife4j-openapi3-jakarta-spring-boot-starter
- 文件链接：[server/pom.xml](file:///d:/Code/Sky-Takeout-System/skyTakeout/server/pom.xml)

## 构建验证
- 已执行：`mvn -DskipTests clean package`，构建成功。
