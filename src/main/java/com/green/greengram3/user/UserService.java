package com.green.greengram3.user;

import com.green.greengram3.common.Const;
import com.green.greengram3.common.ResVo;
import com.green.greengram3.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;

    public ResVo postSignup(UserSignupDto dto) {
        String savedPw = BCrypt.hashpw(dto.getUpw(), BCrypt.gensalt());
        UserSignProcDto pdto = UserSignProcDto
                .builder()
                .uid(dto.getUid())
                .upw(savedPw)
                .nm(dto.getNm())
                .pic(dto.getPic())
                .build();
        int result = mapper.insUser(pdto);
        if (result == 0) {
            return new ResVo(0);
        }
        return new ResVo(pdto.getIuser()); // 회원가입한 iuser pk값이 리턴
    }

    public UserSigninVo signin(UserSigninDto dto) {
        UserSelDto sDto = new UserSelDto();
        sDto.setUid(dto.getUid());

        UserEntity entity = mapper.selUser(sDto);
        if(entity == null) {
            return UserSigninVo.builder().result(Const.LOGIN_NO_UID).build();
        } else if(!BCrypt.checkpw(dto.getUpw(), entity.getUpw())) {
            return UserSigninVo.builder().result(Const.LOGIN_WRONG_UPW).build();
        }

        return UserSigninVo.builder()
                .result(Const.SUCCESS)
                .iuser(entity.getIuser())
                .nm(entity.getNm())
                .pic(entity.getPic())
                .build();
    }

    public ResVo patchUserFirebaseToken(UserFirebaseTokenPatchDto dto) {
        int affectedRows = mapper.updUserFirebaseToken(dto);
        return new ResVo(affectedRows);
    }

    public UserInfoVo selUserInfo(UserInfoSelDto dto) {
        return mapper.selUserInfo(dto);
    }

    public ResVo selPatchPic(UserPicDto dto) {
        return new ResVo(mapper.picPatch(dto));
    }

    public ResVo toggleFollow(UserFollowDto dto) {
        int delaffectedRow = mapper.delFollow(dto);
        if (delaffectedRow == 1) {
            return new ResVo(Const.FAIL);
        }
        int affectedRow = mapper.insFollow(dto);
        if (affectedRow == 1) {
            return new ResVo(Const.SUCCESS);
        }
        return null;
    }
}
