package kr.co.study.chatapi.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoomDTO {
    private String roomId;
    private Set<String> participants = new HashSet<>();

    public ChatRoomDTO(String roomId){
        this.roomId = roomId;
    }
}
