package com.green.greengram3.feed;


import com.green.greengram3.BaseIntegrationTest;
import com.green.greengram3.common.ResVo;
import com.green.greengram3.feed.model.FeedInsDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedIntegrationTest extends BaseIntegrationTest {

    @Test
    @Rollback(false)
    public void postFeed() throws Exception {
        FeedInsDto dto = new FeedInsDto();
        dto.setIuser(3);
        dto.setContents("통합 테스트 작업 중");
        dto.setLocation("그린컴퓨터학원");

        List<String> pics = new ArrayList<>();
        pics.add("https://i.pinimg.com/564x/ce/e1/4f/cee14f43a25fb843acc85a2402ccfcb9.jpg");
        pics.add("https://i.pinimg.com/564x/ba/f9/16/baf9167cafba9903be117e19925036b4.jpg");
        dto.setPics(pics);

        String json = om.writeValueAsString(dto);
        System.out.println("json: " + json);

        MvcResult mr = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/feed")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString(); // resvo가 json 형태로 바뀌어서 { result : 1 } 로 기대됨
        // resvo를 string으로 바꾸고 string을 resvo로 바꾸기
        ResVo vo = om.readValue(content, ResVo.class); // content 문자열을 resvo 들어 있는 값으로 변경 (string 값, 바뀌었으면 하는 값)
        assertEquals(true, vo.getResult() > 0);

    }
}
