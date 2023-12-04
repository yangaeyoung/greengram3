package com.green.greengram3.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//스웨거 꾸미기
@Configuration //spring에게 설정과 관련된 것이란 걸 알 수 있음
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Greengram Ver.3")
                        .description("인스타그램 클론 코딩 v3")
                        .version("2.0.0")
                );
    }
}
