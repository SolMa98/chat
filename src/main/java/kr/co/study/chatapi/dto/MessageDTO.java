package kr.co.study.chatapi.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private String channelId;
    private SenderType sender;
    private String senderKey;
    private String content;

    // 메시지 보내는 사람 타입
    public enum SenderType {
        customer,
        agent
    }
}
