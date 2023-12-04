package com.green.greengram3.user.model;

import lombok.Data;

@Data
public class UserInfoVo {
    private int feedCnt;
    private int favCnt;
    private String writerNm;
    private String createdAt;
}
