package com.green.greengram3.user;

import com.green.greengram3.user.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insUser (UserSignProcDto dto);
    UserEntity selUser (UserSelDto dto);
    int picPatch (UserPicDto dto);
    UserInfoVo selUserInfo (UserInfoSelDto dto);
    int insFollow (UserFollowDto dto);
    int delFollow (UserFollowDto dto);
}
