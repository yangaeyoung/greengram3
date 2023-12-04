package com.green.greengram3.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FeedCommentInsDto {
    @JsonIgnore
    private int ifeedComment;

    private int iuser;
    private int ifeed;
    private String comment;
}
