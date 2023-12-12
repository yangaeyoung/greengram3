package com.green.greengram3.dm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class DmInsDto {
    @JsonIgnore
    private int idm; //auto_increment 값 가져오기 위함
    private int loginedIuser;
    private int otherPersonIuser;
}
