package com.myb.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.jwt")
@Data
public class JwtProperties {

    /**
     * 管理端员工生成  jwt 令牌相关配置
     */
    private String adminSecretKey;   // 管理端登录用户
    private long adminTtl;           // 管理端 JWT 令牌生效时间
    private String adminTokenName;   // 管理端 JWT 令牌名称

    /**
     * 用户端微信用户生成 jwt 令牌相关配置
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}
