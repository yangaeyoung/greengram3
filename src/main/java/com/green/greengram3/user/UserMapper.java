package com.green.greengram3.user;

import com.green.greengram3.user.model.UserSignProcDto;
import com.green.greengram3.user.model.UserSigninDto;
import com.green.greengram3.user.model.UserSigninProcDto;
import com.green.greengram3.user.model.UserSigninVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insUser (UserSignProcDto dto);
    UserSigninProcDto selUser (String uid);
}
