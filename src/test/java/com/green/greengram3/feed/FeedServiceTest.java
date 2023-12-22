package com.green.greengram3.feed;

import com.green.greengram3.common.Const;
import com.green.greengram3.common.ResVo;
import com.green.greengram3.feed.model.*;
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
@Import({FeedService.class}) // 빈등록, 여러 개 가능
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
        when(picMapper.insPicFeed(any())).thenReturn(3); // 사진은 여러 장 가능하니 1이 아닌 2,3도 가능

        FeedInsDto dto = new FeedInsDto();
        dto.setIfeed(105); // assertEquals(dto.getIfeed(), vo.getResult()); 때문에 dto.setIfeed 값 넣어줌
        ResVo vo = service.postFeed(dto);
        assertEquals(dto.getIfeed(), vo.getResult(), "auto_increament 제대로 실행 X");
        // ifeed 값 세팅: FeedService에서 mapper.insFeed 가 실행되면 auto_increment로 인해서 ifeed값이 생성되어
        // 제대로 실행되어 return resvo에 ifeed가 제대로 turn이 되는지 확인하기 위해서

        verify(mapper).insFeed(any()); // 그 안에서 진짜 호출되었는지 (값을 사용했는지는 알 수 없음)
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




//        for (FeedSelVo vo : list) { // 우리가 임으로 지정한 값이 list에 들어가고 위의 list가 FeedService에서의 list로
//            assertNotNull(vo.getPics());
//            System.out.println(vo);
//        }

        List<FeedCommentSelVo> cmtsFeed1 = new ArrayList<>();

        FeedCommentSelVo cmtVo1_1 = new FeedCommentSelVo();
        cmtVo1_1.setIfeedComment(1);
        cmtVo1_1.setComment("1-cmtVo1_1");

        FeedCommentSelVo cmtVo1_2 = new FeedCommentSelVo();
        cmtVo1_2.setIfeedComment(2);
        cmtVo1_2.setComment("2-cmtVo1_2");

        cmtsFeed1.add(cmtVo1_1);
        cmtsFeed1.add(cmtVo1_2);

        List<FeedCommentSelVo> cmtsFeed2 = new ArrayList<>();

        FeedCommentSelVo cmtVo2_1 = new FeedCommentSelVo();
        cmtVo2_1.setIfeedComment(3);
        cmtVo2_1.setComment("3-cmtVo2_1");

        FeedCommentSelVo cmtVo2_2 = new FeedCommentSelVo();
        cmtVo2_2.setIfeedComment(4);
        cmtVo2_2.setComment("4-cmtVo2_2");

        FeedCommentSelVo cmtVo2_3 = new FeedCommentSelVo();
        cmtVo2_3.setIfeedComment(5);
        cmtVo2_3.setComment("5-cmtVo2_3");

        FeedCommentSelVo cmtVo2_4 = new FeedCommentSelVo();
        cmtVo2_4.setIfeedComment(6);
        cmtVo2_4.setComment("6-cmtVo2_4");

        cmtsFeed2.add(cmtVo2_1);
        cmtsFeed2.add(cmtVo2_2);
        cmtsFeed2.add(cmtVo2_3);
        cmtsFeed2.add(cmtVo2_4);

        FeedCommentSelDto fcDto1 = new FeedCommentSelDto();
        fcDto1.setStartIdx(0); // 파라미터에 담긴 값 그대로 넣어야 함
        fcDto1.setRowCount(Const.FEED_COMMENT_FIRST_CNT);
        fcDto1.setIfeed(fcDto1.getIfeed());
        when(commentMapper.selFeedCommentAll(any())).thenReturn(cmtsFeed1);


        FeedCommentSelDto fcDto2 = new FeedCommentSelDto();
        fcDto2.setStartIdx(0);
        fcDto2.setRowCount(Const.FEED_COMMENT_FIRST_CNT);
        fcDto2.setIfeed(2);
        when( commentMapper.selFeedCommentAll(fcDto2) ).thenReturn(cmtsFeed2);


        List<FeedCommentSelVo> commentsResult1 = list.get(0).getComments();
        assertEquals(cmtsFeed1, commentsResult1, "ifeed(1) 댓글 체크");
        assertEquals(0, list.get(0).getIsMoreComment(), "ifeed(1) isMoreComment 체크");
        assertEquals(2, list.get(0).getComments().size(), "ifeed(1) commentSize 체크");

        List<FeedCommentSelVo> commentsResult2 = list.get(1).getComments();
        assertEquals(cmtsFeed2, commentsResult2, "ifeed(2) 댓글 체크");
        assertEquals(1, list.get(1).getIsMoreComment(), "ifeed(2) isMoreComment 체크");
        assertEquals(3, cmtsFeed2.size());


        FeedSelDto dto = new FeedSelDto();
        List<FeedSelVo> result = service.selFeedAll(dto); // 뭐가 넘어 오는지 모르지만 어떤 값이 넘어 올 것이란 것만 알 수 있음（진짜 호출）

        for(int i=0; i<result.size(); i++) {
            FeedSelVo rVo = result.get(i);
            FeedSelVo pVo = list.get(i);


//            System.out.println("rVo: " + rVo.getPics());
//            System.out.println("rVo: " + rVo.getContents());
//            System.out.println("rVo: " + rVo.getComments());

            assertEquals(pVo.getIfeed(), rVo.getIfeed());
            assertEquals(pVo.getContents(), rVo.getContents());
            assertEquals(pVo.getComments(), rVo.getComments());
            System.out.println(pVo.getComments());
        }

//        result & list 주소값 복사(셸로 카피)
//        System.out.println(list == result); == ture
        assertEquals(list, result);


    }
}