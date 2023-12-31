package com.green.greengram3.dm;

import com.green.greengram3.dm.model.*;
import com.green.greengram3.user.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DmMapper {
    //----------------------------------- t_dm
    int insDm(DmInsDto dto);
    List<DmSelVo> getDmAll (DmSelDto dto);
    UserEntity selOtherPersonByLoginUser(DmMsgInsDto dto);
    int updDmLastMsg(DmMsgInsDto dto);
    int updDmLastMsgAfterDelByLastMsg(DmMsgDelDto dto);

    //----------------------------------- t_dm_user
    int insDmUser(DmUserInsDto dto); // dm 유저 등록
    Integer selDmUserCheck (DmInsDto dto);


    //----------------------------------- t_dm_msg
    List<DmMsgSelVo> selDmMsgAll (DmMsgSelDto dto); // dm 전체 화면 출력
    int insDmMsg(DmMsgInsDto dto); // dm 보내기
    int delDmMsg(DmMsgDelDto dto); // dm 삭제
}
