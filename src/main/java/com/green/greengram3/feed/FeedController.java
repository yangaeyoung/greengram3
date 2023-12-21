package com.green.greengram3.feed;

import com.green.greengram3.common.ResVo;
import com.green.greengram3.feed.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
@Tag(name = "피드 API", description = "피드 관련 처리")
public class FeedController {
    private final FeedService service;

    @Operation(summary = "피드 등록", description = "피드 등록 처리")
    @PostMapping
    public ResVo postFeed(@RequestBody FeedInsDto dto){
        return service.postFeed(dto);
    }

    @GetMapping
    @Operation(summary = "피드 리스트", description = "전체 피드 리스트, 특정 사용자 프로필 화면에서 사용할 피드 리스트")
    public List<FeedSelVo> getFeedAll (FeedSelDto dto){
        log.info("dto = {}",dto);
        return service.selFeedAll(dto);
    }

    @GetMapping("/fav")
    @Operation(summary = "좋아요 toggle", description = "toggle로 처리함<br>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 처리 : result(1), 좋아요 철회 : result(0)")
    })
    public ResVo favFeed(FeedFavDto dto) {
        return service.toggleFavFeed(dto);
    }

    @DeleteMapping
    public ResVo delFeed (FeedDelDto dto){
        return service.delFeed(dto);
    }

}
