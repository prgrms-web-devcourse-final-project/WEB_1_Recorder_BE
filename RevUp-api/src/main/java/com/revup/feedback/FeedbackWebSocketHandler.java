package com.revup.feedback;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.nio.ByteBuffer;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FeedbackWebSocketHandler extends BinaryWebSocketHandler {

    private final ConcurrentHashMap<String, Set<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        String uri = session.getUri().toString();  // URL 경로 추출
        System.out.println(uri); // ws://localhost:8080/ws/feedback/1 로 찍힘

        if (uri.contains("/ws/feedback")) {
            String roomId = uri.substring(uri.lastIndexOf("/") + 1);
            System.out.println("Room ID: " + roomId);

            roomSessions.computeIfAbsent("/ws/feedback/" + roomId, k -> ConcurrentHashMap.newKeySet()).add(session);
            System.out.println("새로운 클라이언트가 생긴 피드백방 번호: " + roomId);
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        ByteBuffer payload = message.getPayload();
        System.out.println("Received binary message: " + payload);

        String uri = session.getUri().toString();
        Set<WebSocketSession> targetSessions = null;
        String roomId = uri.substring(uri.lastIndexOf("/") + 1);

        // 메시지를 보낼 경로를 설정
        targetSessions = roomSessions.get("/ws/feedback/" + roomId);

        // 경로에 맞는 세션들에 메시지를 브로드캐스트
        if (targetSessions != null) {
            for (WebSocketSession wsSession : targetSessions) {
                if (wsSession.isOpen() && !wsSession.getId().equals(session.getId())) {
                    wsSession.sendMessage(new BinaryMessage(payload));
                }
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("Transport error: " + exception.getMessage());
        removeSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Connection closed: " + session.getId());
        removeSession(session);
    }

    // 세션을 경로별로 제거
    private void removeSession(WebSocketSession session) {
        String uri = session.getUri().toString();
        roomSessions.get("/ws/feedback/" + uri.substring(uri.lastIndexOf("/") + 1)).remove(session);
    }

}
