package com.green.greengram3.dm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.green.greengram3.common.Const;
import com.green.greengram3.common.ResVo;
import com.green.greengram3.dm.model.*;
import com.green.greengram3.user.UserMapper;
import com.green.greengram3.user.model.UserEntity;
import com.green.greengram3.user.model.UserSelDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DmService {
    private final DmMapper mapper;
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;


    public List<DmSelVo> getDmAll(DmSelDto dto) {
        return mapper.getDmAll(dto);
    }

    public ResVo postDmMsg(DmMsgInsDto dto) {
        int insAffectedRows = mapper.insDmMsg(dto);
        //last msg update
        if(insAffectedRows == 1) {
            int updAffectedRows = mapper.updDmLastMsg(dto);
        }
        LocalDateTime now = LocalDateTime.now(); // 현재 날짜 구하기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 포맷 정의
        String createdAt = now.format(formatter); // 포맷 적용

        //상대방의 firebaseToken값 필요. 나의 pic, iuser값 필요.
        UserEntity otherPerson = mapper.selOtherPersonByLoginUser(dto);

        try {

            if(otherPerson.getFirebaseToken() != null) {
                DmMsgPushVo pushVo = new DmMsgPushVo();
                pushVo.setIdm(dto.getIdm());
                pushVo.setSeq(dto.getSeq());
                pushVo.setWriterIuser(dto.getLoginedIuser());
                pushVo.setWriterPic(dto.getLoginedPic());
                pushVo.setMsg(dto.getMsg());
                pushVo.setCreatedAt(createdAt);

                //object to json
                String body = objectMapper.writeValueAsString(pushVo);
                log.info("body: {}", body);
                Notification noti = Notification.builder()
                        .setTitle("dm")
                        .setBody(body)
                        .build();

                Message message = Message.builder()
                        .setToken(otherPerson.getFirebaseToken())

                        .setNotification(noti)
                        .build();

                FirebaseMessaging.getInstance().sendAsync(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        int delAffectedRows = mapper.delDmMsg(dto);
        if(delAffectedRows == 1) {
            int updAffectedRows = mapper.updDmLastMsgAfterDelByLastMsg(dto);
        }
        return new ResVo(delAffectedRows);
    }
}
