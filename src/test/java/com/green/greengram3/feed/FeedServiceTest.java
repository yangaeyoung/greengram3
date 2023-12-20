package com.green.greengram3.feed;

import com.green.greengram3.common.ResVo;
import com.green.greengram3.feed.model.FeedInsDto;
import com.green.greengram3.feed.model.FeedSelDto;
import com.green.greengram3.feed.model.FeedSelVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // Spring 컨테이너로 올라오게 끔 함, 단위 테스트
@Import({FeedService.class}) // 빈등록
class FeedServiceTest {

    @MockBean // 객체 주소 값이 있는 것처럼 가짜 bean을 등록, 하나라도 없으면 에러
    private FeedMapper mapper;
    @MockBean
    private FeedPicMapper picMapper;
    @MockBean
    private FeedFavMapper favMapper;
    @MockBean
    private FeedCommentMapper commentMapper;
    @Autowired // 가짜 bean 등록
    private FeedService service;

    @Test
    void postFeed() {
        when(mapper.insFeed(any())).thenReturn(1); // insFeed가 호출되고 입력되면 1을 return service에서 log.info
        when(picMapper.insPicFeed(any())).thenReturn(3);

        FeedInsDto dto = new FeedInsDto();
        dto.setIfeed(105);
        ResVo vo = service.postFeed(dto);
        assertEquals(dto.getIfeed(), vo.getResult());

        verify(mapper).insFeed(any()); // 그 안에서 진짜 호출되었는지
        verify(picMapper).insPicFeed(any());

        FeedInsDto dto2 = new FeedInsDto();
        dto.setIfeed(0);
        ResVo vo2 = service.postFeed(dto);
        assertEquals(dto2.getIfeed(), vo2.getResult());
    }

    @Test
    void getFeedAll() {
        FeedSelVo feedSelVo1 = new FeedSelVo();
        feedSelVo1.setIfeed(1);
        feedSelVo1.setContents("일번 feedSelVo");

        FeedSelVo feedSelVo2 = new FeedSelVo();
        feedSelVo2.setIfeed(2);
        feedSelVo2.setContents("이번 feedSelVo");

        List<FeedSelVo> list = new ArrayList<>(); //FeedSelVo 객체의 주소값을 담기 = list
        list.add(feedSelVo1);
        list.add(feedSelVo2);
        when(mapper.selFeedAll(any())).thenReturn(list); // mapper.selFeedAll이 호출되면 list를 return

        List<String> feed1Pics = Arrays.stream(new String[]{"a.jpg", "b.jpg"}).toList(); // 배열을 문자열로 변경
        List<String> feed2Pics = new ArrayList<>();
        feed2Pics.add("가.jpg");
        feed2Pics.add("나.jpg");

        when(picMapper.selPicFeed(1)).thenReturn(feed1Pics); // mapper을 실행 시키고(FeedService에서) 원하는 게(위에 임의로 지정한 값) return되도록 설정할 수 있음
        when(picMapper.selPicFeed(2)).thenReturn(feed2Pics);

        FeedSelDto dto = new FeedSelDto();
        List<FeedSelVo> result = service.selFeedAll(dto); // 뭐가 넘어 오는지 모르지만 어떤 값이 넘어 올 것이란 것만 알 수 있음（진짜 호출）

        // result & list 주소값 복사(셸로 카피)
//        System.out.println(list == result); == ture
        assertEquals(list, result);

        for(FeedSelVo vo : list) { // 우리가 임으로 지정한 값이 list에 들어가고 위의 list가 FeedService에서의 list로
            assertNotNull(vo.getPics());
            System.out.println(vo);
        }

//        for(int i=0; i<result.size(); i++) {
//            FeedSelVo rVo = result.get(i);
//            FeedSelVo pVo = list.get(i);
//
//            System.out.println(rVo.getPics());
//            System.out.println(rVo.getContents());
//
//            assertEquals(pVo.getIfeed(), rVo.getIfeed());
//            assertEquals(pVo.getContents(), rVo.getContents());
//        }



    }
}