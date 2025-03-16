package kr.co.study.chatapi.controller;

import kr.co.study.chatapi.dto.ChatRoomDTO;
import kr.co.study.chatapi.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat/room")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    /**
     * 채팅방 리스트 가져오기
     */
    @GetMapping
    public List<ChatRoomDTO> getRoomLists() {
        return chatRoomService.getRooms();
    }

    /**
     * 채팅방 생성
     */
    @PostMapping
    public ChatRoomDTO createRoom(){
        return chatRoomService.createRoom();
    }
}
