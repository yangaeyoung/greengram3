package com.green.greengram3.feed;

import com.green.greengram3.feed.model.FeedDelDto;
import com.green.greengram3.feed.model.FeedInsProcDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedPicMapper {
    int insPicFeed (FeedInsProcDto dto);
    List<String> selPicFeed (int ifeed); // String pic 값을 가져오기 때문에 List<String> 타입으로 설정 가능
    int delFeedPics(FeedDelDto dto);
}
