package com.green.greengram3.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "회원가입 데이터")
public class UserSignupDto {//고객이 입력할 정보들
    @Schema(title = "유저 id", example = "mic")
    private String uid;
    @Schema(title = "유저 pw", example = "1234")
    private String upw;
    @Schema(title = "유저 이름", example = "홍길동")
    private String nm;
    private String pic;
}
