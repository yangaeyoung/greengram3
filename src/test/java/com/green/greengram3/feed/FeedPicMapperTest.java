package com.green.greengram3.feed;


import com.green.greengram3.feed.model.FeedDelDto;

import com.green.greengram3.feed.model.FeedInsProcDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedPicMapperTest {
    private FeedInsProcDto dto;

    public FeedPicMapperTest() {
        List<String> pics = new ArrayList();
        pics.add("a.jpg");
        pics.add("b.jpg");
        pics.add("c.jpg");

        this.dto = FeedInsProcDto.builder()
                .ifeed(7)
                .iuser(1)
                .pics(pics)
                .build();

    }

    @Autowired
    private FeedPicMapper picMapper;

    @BeforeEach
    public void beforeEach() {
        FeedDelDto dto = new FeedDelDto();
        dto.setIfeed(this.dto.getIfeed());
        dto.setIuser(this.dto.getIuser());
        picMapper.delFeedPics(dto);
    }

    @Test
    void insPicFeed() {
//        final int IFEED = 6;
//        List<String> pics = new ArrayList<>();
//        pics.add("1");
//        pics.add("2");
//        int insAffectedRows = picMapper.insPicFeed(dto);
//        assertEquals(pics.size(), insAffectedRows);

        List<String> preList = picMapper.selPicFeed(dto.getIfeed());
        assertEquals(0, preList.size());

        int insAffectedRows = picMapper.insPicFeed(dto);
        assertEquals(dto.getPics().size(), insAffectedRows);

        List<String> afterList = picMapper.selPicFeed(dto.getIfeed());
        assertEquals(dto.getPics().size(), afterList.size());

        for(int i=0; i<dto.getPics().size(); i++){
            assertEquals(dto.getPics().get(i), afterList.get(i));
        }

        // = (for문)
        // assertEquals(dto.getPics().get(0), afterList.get(0));
        // assertEquals(dto.getPics().get(1), afterList.get(1));
    }
}