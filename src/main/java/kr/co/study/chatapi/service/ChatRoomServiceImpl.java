package kr.co.study.chatapi.service;
import kr.co.study.chatapi.dto.ChatRoomDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatRoomServiceImpl implements ChatRoomService{
    private final Map<String, ChatRoomDTO> chatRooms = new HashMap<>();

    // 방 생성
    @Override
    public ChatRoomDTO createRoom() {
        String roomId = UUID.randomUUID().toString();
        ChatRoomDTO room = new ChatRoomDTO(roomId);
        chatRooms.put(roomId, room);
        return room;
    }

    // 모든 방 가져오기
    @Override
    public List<ChatRoomDTO> getRooms(){
        return new ArrayList<>(chatRooms.values());
    }

    // 특정 방 조회
    @Override
    public ChatRoomDTO getRoom(String roomId){
        return chatRooms.get(roomId);
    }
}
