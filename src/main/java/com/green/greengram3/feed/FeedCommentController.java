package com.green.greengram3.feed;

import com.green.greengram3.common.ResVo;
import com.green.greengram3.feed.model.FeedCommentInsDto;
import com.green.greengram3.feed.model.FeedCommentSelVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed/comment")
public class FeedCommentController {
    private final FeedCommentService service;

    @PostMapping
    public ResVo insFeeedCommnet (@RequestBody FeedCommentInsDto dto) {
        log.info("dto = {}",dto);
        return service.insFeeedCommnet(dto);
    }

    @GetMapping
    public List<FeedCommentSelVo> getFeedCommentAll(int ifeed) { // 댓글 4~999까지의 레코드만 리턴될 수 있도록
        return service.getFeedCommentAll(ifeed);
    }
}
