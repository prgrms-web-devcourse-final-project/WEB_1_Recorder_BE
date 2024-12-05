package com.revup.chat.service.response;

import com.revup.chat.entity.ChatMessage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class ChatMessageResponse {

    private String sender;

    private String content;

    private LocalDateTime createdAt;

    public static ChatMessageResponse from(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
                .sender(chatMessage.getChatRoomBelong().getUser().getId().toString())
                .content(chatMessage.getContent())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }

}
