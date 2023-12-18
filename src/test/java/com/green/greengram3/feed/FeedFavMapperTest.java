package com.green.greengram3.feed;

import com.green.greengram3.feed.model.FeedFavDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*; //Assertions.assertEquals()의 Assertions 생략 가능하게 함

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedFavMapperTest {

    @Autowired // DI
    private FeedFavMapper mapper;

    @Test
    public void insFeedFavTest() {
        assertEquals(1, 1);
        FeedFavDto dto = new FeedFavDto();
        dto.setIfeed(8); //db에 있는 값으로
        dto.setIuser(3);

        int insAffectedRows = mapper.insFeedFav(dto);
        assertEquals(1, insAffectedRows, "1");

        dto.setIfeed(14);
        dto.setIuser(3);

        List<FeedFavDto> result = mapper.selFeedFavForTest(dto);
        assertEquals(1, result.size(), "2");

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