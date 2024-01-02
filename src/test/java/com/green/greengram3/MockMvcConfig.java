package com.green.greengram3;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@AutoConfigureMockMvc
@Import({ MockMvcConfig.MockMvc.class }) // 중괄호 사이에 여러 개 적을 수 있음
public @interface MockMvcConfig { // 패키지 안에 있는 feedControllerTest 때문에 public를 붙여줘야 함
    class MockMvc {
        @Bean
        MockMvcBuilderCustomizer utf8Config() {
            return (builder) -> builder.addFilter(new CharacterEncodingFilter("UTF-8", true)); // MockMvc가 통신을 할 때 필터를 추가, 람다식
            // 메소드 호출 파라미터 (builder)로 전달, 괄호 생략 가능 // -> 뒤에 return 생략되어 있음
        }
    }
}
