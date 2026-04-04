# Knife4j文档无法显示数据问题修复

## 问题描述

在访问 `http://localhost:8081/doc.html` 时，Knife4j文档只显示UI界面，不显示API数据，浏览器控制台报JSON解析错误：

```
SyntaxError: Unexpected token 'e', "eyJvcGVuYX"... is not valid JSON
```

## 根本原因分析

1. **消息转换器配置问题**：自定义的 `JacksonObjectMapper` 被设置为首选消息转换器，导致OpenAPI相关的对象也使用了这个自定义转换器，而该转换器可能无法正确处理OpenAPI对象的序列化。

2. **JWT拦截器问题**：`JwtTokenAdminInterceptor` 没有正确处理 `token` 为 `null` 的情况，导致对Knife4j的请求也进行了JWT校验。

3. **依赖冲突**：同时引入了 `knife4j-openapi3-jakarta-spring-boot-starter` 和 `springdoc-openapi-starter-webmvc-ui`，可能导致依赖冲突。

4. **缺少注解**：`WebMvcConfiguration` 类中的 `addInterceptors` 方法缺少 `@Override` 注解，可能导致拦截器配置没有被正确应用。

## 修复步骤

### 1. 修复消息转换器配置

在 `WebMvcConfiguration.java` 中，修改消息转换器的添加方式，将自定义消息转换器添加到容器中，但不是放在第一位，这样可以保证OpenAPI相关的对象使用默认的消息转换器：

```java
@Override
protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    log.info("扩展消息转换器...");
    //创建一个消息转换器对象
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    //需要为消息转换器设置一个对象转换器，对象转换器可以将Java对象序列化为json数据
    converter.setObjectMapper(new JacksonObjectMapper());
    //将自己的消息转化器加入容器中，但不是放在第一位，这样可以保证OpenAPI相关的对象使用默认的消息转换器
    converters.add(converter);
}
```


### 2. 移除冲突依赖

在 `server/pom.xml` 中，移除冲突的 `springdoc-openapi-starter-webmvc-ui` 依赖，只保留 `knife4j-openapi3-jakarta-spring-boot-starter`：

```xml
<!-- 文档与扩展：Knife4j（OpenAPI3 Jakarta） -->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
</dependency>

<!-- 移除冲突的springdoc依赖 -->
<!-- <dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency> -->
```

### 3. 添加缺失的注解

在 `WebMvcConfiguration.java` 中，为 `addInterceptors` 方法添加 `@Override` 注解：

```java
@Override
protected void addInterceptors(InterceptorRegistry registry) {
    log.info("开始注册自定义拦截器...");
    registry.addInterceptor(jwtTokenAdminInterceptor)
            .addPathPatterns("/admin/**")
            .excludePathPatterns("/admin/employee/login")
            .excludePathPatterns("/doc.html")
            .excludePathPatterns("/webjars/**")
            .excludePathPatterns("/v3/api-docs/**")
            .excludePathPatterns("/swagger-ui/**");
}
```

在 `JwtTokenAdminInterceptor.java` 中，为 `preHandle` 方法添加 `@Override` 注解：

```java
@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
    // 实现代码...
}
```

## 验证结果

修复后，访问 `http://localhost:8081/doc.html`，Knife4j文档现在可以正常显示API数据，包括：

1. 页面标题显示为"苍穹外卖API"
2. 完整的菜单结构（主页、Swagger Models、文档管理等）
3. API的详细信息（简介、版本、接口统计等）
4. 分组信息（[admin]分类管理、[admin]员工管理等）
5. 浏览器控制台没有任何错误信息

## 总结

通过以下步骤成功修复了Knife4j文档无法显示数据的问题：

1. 调整了消息转换器的配置，确保OpenAPI相关的对象使用默认的消息转换器
2. 移除了冲突的springdoc依赖
3. 添加了缺失的@Override注解

这些修改确保了Knife4j能够正确处理API文档的生成和显示，同时不影响系统的其他功能。