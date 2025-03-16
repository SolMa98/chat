package kr.co.study.chatapi.service;

import kr.co.study.chatapi.dto.ChatRoomDTO;

import java.util.List;
public interface ChatRoomService {
    /**
     * 채팅방 생성
     */
    public ChatRoomDTO createRoom();

    /**
     * 전체 채팅방 리스트 가져오기
     */
    public List<ChatRoomDTO> getRooms();

    /**
     * 특정 채팅방 가져오기
     */
    public ChatRoomDTO getRoom(String roomId);
}
