package com.green.greengram3.user.model;

import lombok.Data;

@Data
public class UserSigninVo { //pk값, 이름, 사진
    private int result;
    private int iuser;
    private String nm;
    private String pic;
}
