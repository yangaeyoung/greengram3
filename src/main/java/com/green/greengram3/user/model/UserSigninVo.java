package com.green.greengram3.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSigninVo { //pk값, 이름, 사진
    private int result;
    private int iuser;
    private String nm;
    private String pic;
    private String firebaseToken;
}
