package com.green.greengram3.dm;

import com.green.greengram3.common.Const;
import com.green.greengram3.common.ResVo;
import com.green.greengram3.dm.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DmService {
    private final DmMapper mapper;



    public List<DmSelVo> getDmAll(DmSelDto dto){
        return mapper.getDmAll(dto);
    }

    public ResVo postDmMsg(DmMsgInsDto dto){
        int affectedRows = mapper.insDmMsg(dto);
        return new ResVo(dto.getSeq());
    }

    public List<DmMsgSelVo> getMsgAll(DmMsgSelDto dto){
        log.info("dto:{}", dto);
        return mapper.selDmMsgAll(dto);
    }

    public ResVo delDmMsg(DmMsgDelDto dto) {
        int affectedRows = mapper.delDmMsg(dto);
        if(affectedRows == 0){
            return new ResVo(Const.FAIL);
        }
        return new ResVo(Const.SUCCESS);
    }
}
