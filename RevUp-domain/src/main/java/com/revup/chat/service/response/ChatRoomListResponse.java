package com.revup.chat.service.response;

import com.revup.chat.entity.ChatMessage;
import com.revup.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class ChatRoomListResponse {

    private Long opponentId;

    private String latestMessage;

    private LocalDateTime createdAt;

    public static ChatRoomListResponse from(ChatMessage chatMessage, User user) {
        return ChatRoomListResponse.builder()
                .opponentId(user.getId())
                .latestMessage(chatMessage.getContent())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
