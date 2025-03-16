package kr.co.study.chatapi.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    // 연결된 클라이언트를 관리할 Set
    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 클라이언트가 연결되면 세션에 추가
        sessions.add(session);
        System.out.println("New connection established: " + session.getId());
        sendClientListToAll();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, org.springframework.web.socket.TextMessage message) throws Exception {
        // 메시지 처리 로직 (예: 클라이언트에게 메시지 전송)
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        // 클라이언트가 연결을 끊으면 세션에서 제거
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
        sendClientListToAll();
    }

    private void sendClientListToAll() {
        // 모든 연결된 클라이언트에게 현재 연결된 클라이언트 목록을 전송
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new org.springframework.web.socket.TextMessage("Connected clients: " + sessions.size()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}