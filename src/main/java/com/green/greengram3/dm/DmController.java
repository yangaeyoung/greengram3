package com.green.greengram3.dm;

import com.green.greengram3.common.ResVo;
import com.green.greengram3.dm.model.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dm")
public class DmController {
    private final DmService service;


    @GetMapping
    public List<DmSelVo> getDmAll(DmSelDto dto) {
        return service.getDmAll(dto);
    }

    @PostMapping("/msg") // last_msg 마지막 대화 불러오기
    public ResVo postDmMsg(@RequestBody DmMsgInsDto dto) {
        return service.postDmMsg(dto);
    }

    // dm창을 새로 만들 때 필요한 값 : iuser 값 두 개
    // ResVo : 위의 list들을 불러와야 함
    // DmSelVo
    @PostMapping
    public DmSelVo postDm(@RequestBody DmInsDto dto){
        return service.postDm(dto);
    }

    @GetMapping("/msg")
    public List<DmMsgSelVo> getMsgAll(DmMsgSelDto dto) { //파라미터 객체로 받을 때는 @RequestParam ture면 안 됨 //int나 String으로 따로 적을 때는 @RequestParam가 붙은 효과가 남
        return service.getMsgAll(dto);
    }

    @DeleteMapping("/msg")
    public ResVo delDmMsg(DmMsgDelDto dto) {
        return service.delDmMsg(dto);
    }
}
