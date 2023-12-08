package com.green.greengram3.user.model;

import lombok.Data;

@Data
public class UserInfoVo {
    private int feedCnt;
    private int favCnt;
    private String nm;
    private String createdAt;
    private String pic;
    private int follower; //팔로워 수(targetIuser를 팔로우 하는 상황)
    private int following; //팔로잉 수 (targetIuser가 팔로우 한 상황)
    private int followState;
    //1-loginedIuser가 targetIuser를 팔로우 한 상황
    //2-targetIuser가 loginedIuser를 팔로우만 한 상황
    //3-둘 다 팔로우만 한 상황

}
