package kr.co.study.chatapi.controller;

import kr.co.study.chatapi.dto.ChatRoomDTO;
import kr.co.study.chatapi.dto.MessageDTO;
import kr.co.study.chatapi.service.ChatRoomService;
import lombok.RequiredArgsConstructor;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ChatRoomService chatRoomService;
    /**
     * 클라이언트가 /pub/chat/message 로 메시지를 발행
     *
     * /sub/{roomId} - 구독
     * /pub/chat/message             - 메시지 발생
     * @param message 메시지
     */
    @MessageMapping("/chat/message")
    public void message(final MessageDTO message){
        simpMessageSendingOperations.convertAndSend("/sub/" + message.getRoomId(), message);
    }

    /**
     * 전체 채팅방에 대하여 채팅방 리스트 새로 호출
     */
    @MessageMapping("/chat/new")
    public void newChatListNotice() {
        try{
            // 전체 방 대상으로 새로운 방 생성 소켓 메시지 전송
            List<ChatRoomDTO> roomDTOList = chatRoomService.getRooms();
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setSender(MessageDTO.SenderType.system);
            messageDTO.setSenderKey("SYSTEM");
            messageDTO.setContent("NEW_ROOM");
            for(ChatRoomDTO roomDTO : roomDTOList){
                messageDTO.setRoomId(roomDTO.getRoomId());
                simpMessageSendingOperations.convertAndSend("/sub/" + roomDTO.getRoomId(), messageDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
