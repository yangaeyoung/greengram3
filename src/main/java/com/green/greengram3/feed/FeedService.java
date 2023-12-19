package com.green.greengram3.feed;

import com.green.greengram3.common.Const;
import com.green.greengram3.common.ResVo;
import com.green.greengram3.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {
    private final FeedMapper mapper;
    private final FeedPicMapper picMapper;
    private final FeedFavMapper favMapper;
    private final FeedCommentMapper commentMapper;

    public ResVo postFeed(FeedInsDto dto) {
        FeedInsProcDto pdto = FeedInsProcDto
                .builder()
                .ifeed(dto.getIfeed())
                .iuser(dto.getIuser())
                .contents(dto.getContents())
                .location(dto.getLocation())
                .pics(dto.getPics())
                .build();
        //트랜잭션 묶기
        int result = mapper.insFeed(pdto);
        log.info("result : {}", result);
        int result2 = picMapper.insPicFeed(pdto);// 사진의 사이즈 값 저장
        log.info("result2 : {}", result2);

//        if (result == 0 || pdto.getIfeed() == 0) { //사진이 없을 경우 튕겨내기
//            return new ResVo(2);
//        }
//        if (result2 != dto.getPics().size()) {
//            return new ResVo(0);
//        }
        return new ResVo(pdto.getIfeed());
    }

    // select문을 4번만 하면 되는데 +1을 했기 때문에 n+1의 방식이라고 함
    public List<FeedSelVo> selFeedAll(FeedSelDto dto) {
        List<FeedSelVo> list = mapper.selFeedAll(dto);

        FeedCommentSelDto fcDto = new FeedCommentSelDto();
        fcDto.setStartIdx(0);
        fcDto.setRowCount(Const.FEED_COMMENT_FIRST_CNT);

        for (FeedSelVo vo : list) {
            List<String> pics = picMapper.selPicFeed(vo.getIfeed());
            vo.setPics(pics);
            fcDto.setIfeed(vo.getIfeed()); // 위치
            List<FeedCommentSelVo> comments = commentMapper.selFeedCommentAll(fcDto);
            vo.setComments(comments);
            vo.setIsMoreComment(0);
            if (comments.size() == Const.FEED_COMMENT_FIRST_CNT) {
                vo.setIsMoreComment(1);
                comments.remove(comments.size() - 1);
            }
        }
        return list;
    }

    public ResVo togglefavFeed(FeedFavDto dto) {
        int result = favMapper.delFeedFav(dto);
        if (result == 0) {
            int insAffectedRow = favMapper.insFeedFav(dto);
            return new ResVo(Const.FEED_FAV_ADD);
        }
        return new ResVo(Const.FEED_FAV_DEL);
    }

    public ResVo delFeed(FeedDelDto dto) { //트랜잭션으로 롤백하는 방법도 있음 (@Transactional 붙이고 데이터베이스 연동하는 부분에서 세팅 필요)
        int affectedRow = mapper.selFeed(dto);
        if(affectedRow == Const.SUCCESS){
            mapper.delFeedAll(dto);
            return new ResVo(mapper.delFeed(dto));
        }
        return new ResVo(Const.FAIL);
    }
}
