package com.revup.chat.service.response;

import com.revup.chat.entity.ChatMessage;
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

//    public static ChatResponse from(ChatMessage chatMessage) {
//        return ChatResponse.builder()
//                .senderName(chatMessage.getSender().getSocialId())
//                .receiverName(chatMessage.getReceiver().getSocialId())
//                .content(chatMessage.getContent())
//                .isRead(chatMessage.getIsRead())
//                .createdAt(chatMessage.getCreatedAt())
//                .build();
//    }

}
