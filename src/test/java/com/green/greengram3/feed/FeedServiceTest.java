package com.green.greengram3.feed;

import com.green.greengram3.common.ResVo;
import com.green.greengram3.feed.model.FeedInsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({FeedService.class})
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
        dto.setIfeed(106);
        ResVo vo2 = service.postFeed(dto);
        assertEquals(dto2.getIfeed(), vo2.getResult());
    }
}