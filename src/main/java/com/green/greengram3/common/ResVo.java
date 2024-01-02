package com.green.greengram3.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // readValue가 있을 때 기본 생성자 어노테이션 있어야 함
public class ResVo {
    private int result;
}
