package com.green.greengram3.feed;

import com.green.greengram3.common.Const;
import com.green.greengram3.common.ResVo;
import com.green.greengram3.feed.model.FeedCommentInsDto;
import com.green.greengram3.feed.model.FeedCommentSelDto;
import com.green.greengram3.feed.model.FeedCommentSelVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentMapper commentMapper;

    public ResVo insFeeedCommnet (FeedCommentInsDto dto) {
            int result = commentMapper.insFeeedCommnet(dto);
            return new ResVo(dto.getIfeedComment());
    }

    public List<FeedCommentSelVo> getFeedCommentAll(int ifeed) {
        FeedCommentSelDto dto = new FeedCommentSelDto();
        dto.setIfeed(ifeed);
        dto.setStartIdx(3);
        dto.setRowCount(999);
        return commentMapper.selFeedCommentAll(dto);
    }
}
