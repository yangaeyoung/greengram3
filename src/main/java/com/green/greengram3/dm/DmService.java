package com.green.greengram3.dm;

import com.green.greengram3.common.Const;
import com.green.greengram3.common.ResVo;
import com.green.greengram3.dm.model.*;
import com.green.greengram3.user.UserMapper;
import com.green.greengram3.user.model.UserEntity;
import com.green.greengram3.user.model.UserSelDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DmService {
    private final DmMapper mapper;
    private final UserMapper userMapper;


    public List<DmSelVo> getDmAll(DmSelDto dto) {
        return mapper.getDmAll(dto);
    }

    public ResVo postDmMsg(DmMsgInsDto dto) {
        int affectedRows = mapper.insDmMsg(dto);
        //last_msg update
//        if(affectedRows == 1){
//            int upAffectedRow = mapper.updDmLstMsg(dto);
//        }
        return new ResVo(dto.getSeq());
    }

    public DmSelVo postDm(DmInsDto dto){
        mapper.insDm(dto); // t_dm 테이블에 null값을 넣어서 idm pk 값 생성

        mapper.insDmUser(DmUserInsDto.builder()
                .idm(dto.getIdm())
                .iuser(dto.getLoginedIuser())
                .build());
        mapper.insDmUser(DmUserInsDto.builder()
                .idm(dto.getIdm())
                .iuser(dto.getOtherPersonIuser())
                .build());
        // 생성된 대화방(idm)에 대화자(iuser) 두 명 넣기

        UserSelDto uDto= new UserSelDto(); // iuser, uid 값을 사용하기 위해 객체 생성
        uDto.setIuser(dto.getOtherPersonIuser());
        UserEntity entity= userMapper.selUser(uDto); // dm방에서 상대의 프로필 불러오기

        // lastMsg, lastMsgAt 값 null
        return DmSelVo.builder().idm(dto.getIdm())
                .otherPersonIuser(entity.getIuser())
                .otherPersonNm(entity.getNm())
                .otherPersonPic(entity.getPic())
                .build();
    }

    public List<DmMsgSelVo> getMsgAll(DmMsgSelDto dto) {
        log.info("dto:{}", dto);
        return mapper.selDmMsgAll(dto);
    }

    public ResVo delDmMsg(DmMsgDelDto dto) {
        int affectedRows = mapper.delDmMsg(dto);
        if (affectedRows == 0) {
            return new ResVo(Const.FAIL);
        }
        return new ResVo(Const.SUCCESS);
    }
}
