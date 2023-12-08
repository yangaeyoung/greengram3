package com.green.greengram3.dm;

import com.green.greengram3.dm.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DmMapper {
    //----------------------------------- t_dm
    int insDm(DmInsDto dto);
    List<DmSelVo> getDmAll (DmSelDto dto);

    //----------------------------------- t_dm_user
    int insDmUser(DmUserInsDto dto);


    //----------------------------------- t_dm_msg
    List<DmMsgSelVo> selDmMsgAll (DmMsgSelDto dto);
    int insDmMsg(DmMsgInsDto dto);
}
