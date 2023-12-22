package com.green.greengram3.feed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram3.common.ResVo;
import com.green.greengram3.feed.model.FeedDelDto;
import com.green.greengram3.feed.model.FeedInsDto;
import com.green.greengram3.feed.model.FeedSelDto;
import com.green.greengram3.feed.model.FeedSelVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeedController.class) // 빈등록 객체 상태로 등록
@Slf4j
class FeedControllerTest {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;
    // 여기까지 작성하고 실행하면 에러 발생: service 객체화 X -> mockbean // 신호 전송
    @MockBean
    private FeedService service;

    @Test
    void postFeed() throws Exception {
//        ResVo vo = service.postFeed(new FeedInsDto());
//        System.out.println(vo); // -> null autowired & mockbean 테스트

        ResVo result = new ResVo(5); // Controller에 어노테이션 @RestController json 형태로 넘어옴
//        when(service.postFeed(any())).thenReturn(result);
        given(service.postFeed(any())).willReturn(result); // 가짜에게 임무 부여
        // given: 세팅, when: 실행, then: 실행한 결과

        FeedInsDto dto = new FeedInsDto();
        String json = mapper.writeValueAsString(dto);
        System.out.println("json: " + json);
        log.info("mapper.writeValueAsString(result):{}", mapper.writeValueAsString(result));

        mvc.perform( // 오버로딩
                        MockMvcRequestBuilders
                                .post("/api/feed") // get, post 등 방식 지정
                                .contentType(MediaType.APPLICATION_JSON) // 데이터 json 형식으로 날리기
                                .content(json) // body 부분 json으로 받기, 위 두 개는 헤더 부분
                )
                .andExpect(status().isOk()) // status: 성공 값 {"result" : 5}
                .andExpect(content().string(mapper.writeValueAsString(result))) // json 형태로 돌아올 것을 기대함
                .andDo(print()); // 결과 프린터

        verify(service).postFeed(any()); // Mock Object 메소드가 정해진 횟수만큼 호출 됐는지
    }

    @Test
    void getFeedAll() throws Exception{

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page","2");
        params.add("loginedIuser","2");

        List<FeedSelVo> list = new ArrayList<>();

        FeedSelVo vo = new FeedSelVo(); // Query String으로 dto 말고 vo에다 담아줌
        vo.setIfeed(1);
        vo.setContents("aaa");
        FeedSelVo vo2 = new FeedSelVo();
        vo2.setIfeed(2);
        vo2.setContents("bbb");

        list.add(vo);
        list.add(vo2);
//        when(service.selFeedAll(any())).thenReturn(result);
        given(service.selFeedAll(any())).willReturn(list);

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/feed")
                        .params(params) // 위 params에 값을 따로 넣어주지 않아도 통신 가능
        )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(list)))
                .andDo(print());

        verify(service).selFeedAll(any()); // getFeedAll에 return값이 service.selFeedAll이기 때문에
    }

    @Test
    void favFeed() {
    }

    @Test
    void delFeed() throws Exception {

        ResVo result = new ResVo(5);
        given(service.delFeed(any())).willReturn(result);

//        FeedDelDto dto = new FeedDelDto();
//        dto.setIfeed(1);
//        dto.setIuser(1);
//        String json = mapper.writeValueAsString(dto);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ifeed","2");
        params.add("iuser","2");


        mvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/feed")
                        .params(params) // Query String으로 파라미터 값 입력
//                        .contentType(MediaType.APPLICATION_JSON) Query String 형식으로 json 굳이 할 필요 X
        )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(result)))
                .andDo(print());
        verify(service).delFeed(any());
    }
}