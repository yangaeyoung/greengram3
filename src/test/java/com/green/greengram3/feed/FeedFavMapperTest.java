package com.green.greengram3.feed;

import com.green.greengram3.feed.model.FeedFavDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*; //Assertions.assertEquals()의 Assertions 생략 가능하게 함

@MybatisTest // spring 기동 DAO들이 빈등록(객체화)됨
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 기존 데이터베이스를 바꾸지 않고 검사
class FeedFavMapperTest {

    @Autowired // DI, 생성자 주입할 때 Final 사용 X RequiredArgsConstructor - final과 같음
    private FeedFavMapper mapper;

    @Test
    public void insFeedFavTest() {
        assertEquals(1, 1);
        FeedFavDto dto = new FeedFavDto();
        dto.setIfeed(8); //db에 있는 값으로
        dto.setIuser(3);

        List<FeedFavDto> result = mapper.selFeedFavForTest(dto);
        assertEquals(0, result.size(), "첫번째 insert 전 미리 확인");
        // 값이 없어야 insert를 하기 때문에 0을 기대하는 값으로 넣어줌
        // 이 검증이 없을 경우에 제대로 된 검증이 되었다고 보기 어려움
        // 잘못된 값이 insert 되었는데 조회 했을 때 이미 있는 값이었을 경우 문제 없다고 검증 결과가 뜸

        int insAffectedRows = mapper.insFeedFav(dto);
        assertEquals(1, insAffectedRows, "1");

        List<FeedFavDto> result2 = mapper.selFeedFavForTest(dto);
        assertEquals(1, result2.size(), "2");

    }

    @Test
    public void delFeedFavTest() {
        FeedFavDto selDto = new FeedFavDto();
        selDto.setIfeed(14);
        selDto.setIuser(3);

        int delAffectedRows = mapper.delFeedFav(selDto);
        assertEquals(1, delAffectedRows, "3");

        int delAffectedRows1 = mapper.delFeedFav(selDto);
        assertEquals(0, delAffectedRows1, "4");

        List<FeedFavDto> result2 = mapper.selFeedFavForTest(selDto);
        assertEquals(0, result2.size(), "5"); // null인지 확인해야 함 assertNull
    }

    @Test
    public void delFeedFavAllTest() {
//        FeedFavDto dto = new FeedFavDto();
//        dto.setIfeed(13);
//        int delAffectedRows = mapper.delFeedFav(dto);
//        assertNotNull(delAffectedRows, "6");
        final int ifeed = 13;
        FeedFavDto selDto = new FeedFavDto();
        selDto.setIfeed(ifeed);
        List<FeedFavDto> selList = mapper.selFeedFavForTest(selDto);

        FeedFavDto dto = new FeedFavDto();
        dto.setIfeed(ifeed);
        int delAffectedRows = mapper.delFeedFav(dto);
        assertEquals(0, delAffectedRows);
    }
}