package com.green.greengram3.feed;


import com.green.greengram3.BaseIntegrationTest;
import com.green.greengram3.common.ResVo;
import com.green.greengram3.feed.model.FeedDelDto;
import com.green.greengram3.feed.model.FeedInsDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedIntegrationTest extends BaseIntegrationTest {

    @Test
    @Rollback(false) // true일 때만 데이터 베이스에 저장될 수 있고 false일 때는 롤백이 될 수 있도록
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
                                .contentType(MediaType.APPLICATION_JSON) // json형태로 날리기
                                .content(json) // body에 담아서 보냄
                ) // 신호 쏘면 controller postFeed 파라미터에 담김
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString(); // resvo가 json 형태로 바뀌어서 { "result" : 1 } 로 기대됨
        // resvo를 string으로 바꾸고 string을 resvo로 바꾸기
        ResVo vo = om.readValue(content, ResVo.class); // content 문자열을 resvo 들어 있는 값으로 변경 (string 값, 바뀌었으면 하는 값)
        assertEquals(true, vo.getResult() > 0);

    }

    @Test
    @Rollback(false)
    public void delFeed() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ifeed", "630");
        params.add("iuser", "3");


        MvcResult mr = mvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/feed") // params가 없는 경우 "/api/feed?ifeed=630&iuser=3"
                                // ("/api/feed?ifeed={ifeed}&iuser={iuser}", "630", "3")
                                .params(params)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        ResVo vo = om.readValue(content, ResVo.class);
        assertEquals(1, vo.getResult());
    }
}
