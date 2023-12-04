package com.green.greengram3.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengram3.common.Const;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

@Data
@Schema(title = "피드 리스트")
public class FeedSelDto {
    @Schema(title = "페이지")
    private int page;

    @Schema(title = "로그인한 유저pk")
    private int loginedIuser;

    @JsonIgnore
    private int startIdx;

    @JsonIgnore
    private int rowCount = Const.ROW_COUNT_PER_PAGE;

//    private int targetIuser;

    public void setPage(int page) {
        this.startIdx = (page - 1) * rowCount;
    }
}
