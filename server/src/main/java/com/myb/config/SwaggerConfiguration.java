package com.myb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 配置类
 */
@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("=====> 苍穹外卖接口文档 <=====")
                        .version("1.0")
                        .description("苍穹外卖系统 API 文档"));
    }
}
