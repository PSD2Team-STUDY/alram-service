package com.psd2.studyproject.controller;

import com.psd2.studyproject.dto.SocketMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
@Slf4j
public class OverAllController {
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/socket/test")
    public void socketRole(SocketMessage message) {
        try {
            // ObjectMapper를 사용하여 JSON을 SocketRoleDto 객체로 변환
            log.info(message.toString());
            messagingTemplate.convertAndSend("/sub/socket/"+message.getType()+"/"+ message.getRtcSession(), message);

        }catch (Exception e){
            message.setMessage("error: "+e.getMessage());
            log.info(e.getMessage());
        }
    }
}

