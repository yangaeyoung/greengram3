package com.green.greengram3.user.model;

import lombok.Builder;
import lombok.Data;

@Data
public class UserSigninProcDto {
    private int iuser;
    // private String uid;
    private String upw;
    private String nm;
    private String pic;
}
