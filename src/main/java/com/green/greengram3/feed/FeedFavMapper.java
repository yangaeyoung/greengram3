package com.green.greengram3.feed;

import com.green.greengram3.feed.model.FeedFavDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedFavMapper {
    int insFeedFva (FeedFavDto dto);
    int delFeedFav (FeedFavDto dto);
}
