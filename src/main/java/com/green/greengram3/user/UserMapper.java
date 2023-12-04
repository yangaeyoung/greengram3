package com.green.greengram3.user;

import com.green.greengram3.user.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insUser (UserSignProcDto dto);
    UserSigninProcDto selUser (String uid);
    int picPatch (UserPicDto dto);
    UserInfoVo selUserInfo (int targetIuser);
    int insFollow (UserFollowDto dto);
    int delFollow (UserFollowDto dto);
}
