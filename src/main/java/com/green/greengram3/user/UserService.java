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
        if(result == 0){
            return new ResVo(0);
        }
        return new ResVo(pdto.getIuser()); // 회원가입한 iuser pk값이 리턴
    }

    public UserSigninVo signin(UserSigninDto dto){
        UserSigninProcDto pDto = mapper.selUser(dto.getUid());
        UserSigninVo vo = new UserSigninVo();
        if(pDto == null){
            vo.setResult(Const.LOGIN_NO_UID);
        } else if (!BCrypt.checkpw(dto.getUpw(), pDto.getUpw())) {
            vo.setResult(Const.LOGIN_WRONG_UPW);

        }else {
            vo.setResult(Const.SUCCESS);
            vo.setIuser(pDto.getIuser());
            vo.setNm(pDto.getNm());
            vo.setPic(pDto.getPic());
        }
        return vo;
    }
}
