package com.green.greengram3;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Import(CharEncodongConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 서버 랜덤한 포트를 이용 8080 지정 X
@AutoConfigureMockMvc
@Transactional // 데이터베이스에 진짜로 insert delete X
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트할 때 내용 변경 X 따로 지정한 것으로 값 대입
public class BaseIntegrationTest {
    @Autowired protected MockMvc mvc; // protected: 같은 패키지 안에서 접근 & 패키지가 다르더라도 상속 관계면 접근 가능
    @Autowired protected ObjectMapper om; // ObjectMapper: JSON을 Java 객체로 변환할 수 있고, Java 객체를 JSON 객체
}
