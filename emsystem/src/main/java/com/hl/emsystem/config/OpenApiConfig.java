package com.hl.emsystem.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * spring doc配置
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI restfulOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("湖南工业大学就业管理系统")
                        .description("内部Api,版权归HUT所有")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringDoc维基链接")
                        .url("https://springdoc.org/v2"))
                .servers(Collections.singletonList(new Server()
                        .url("http://http://localhost:8081/")
                        .description("就业管理系统对外端口"))
                );
    }

}
