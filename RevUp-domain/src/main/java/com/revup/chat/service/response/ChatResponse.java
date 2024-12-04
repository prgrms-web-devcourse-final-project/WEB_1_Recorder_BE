package com.revup.chat.service.response;

import com.revup.chat.entity.Chat;
import com.revup.common.BooleanStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class ChatResponse {

    private String senderName;
    private String receiverName;

    private String content;

    private BooleanStatus isRead;

    private LocalDateTime createdAt;

    public static ChatResponse from(Chat chat) {
        return ChatResponse.builder()
                .senderName(chat.getSender().getSocialId())
                .receiverName(chat.getReceiver().getSocialId())
                .content(chat.getContent())
                .isRead(chat.getIsRead())
                .createdAt(chat.getCreatedAt())
                .build();
    }

}
