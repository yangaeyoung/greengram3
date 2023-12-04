package com.green.greengram3.feed;

import com.green.greengram3.feed.model.FeedCommentInsDto;
import com.green.greengram3.feed.model.FeedCommentSelDto;
import com.green.greengram3.feed.model.FeedCommentSelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedCommentMapper {
    int insFeeedCommnet (FeedCommentInsDto dto);
    List<FeedCommentSelVo> selFeedCommentAll (FeedCommentSelDto dto);
}
