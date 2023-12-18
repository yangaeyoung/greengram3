package com.green.greengram3.feed;

import com.green.greengram3.feed.model.FeedDelDto;
import com.green.greengram3.feed.model.FeedFavDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedFavMapper {
    List<FeedFavDto> selFeedFavForTest (FeedFavDto dto);
    int insFeedFav(FeedFavDto dto);
    int delFeedFav (FeedFavDto dto);
}
