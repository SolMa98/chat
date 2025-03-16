package kr.co.study.chatapi.controller;

import kr.co.study.chatapi.dto.MessageDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    /**
     * 클라이언트가 /pub/chat/message 로 메시지를 발행
     *
     * /sub/{channelId} - 구독
     * /pub/chat/message             - 메시지 발생
     * @param message 메시지
     */
    @MessageMapping("/chat/message")
    public void message(final MessageDTO message){
        simpMessageSendingOperations.convertAndSend("/sub/" + message.getChannelId(), message);
    }
}
