package com.revup.chat.dto;

import lombok.Data;

@Data
public class ChatMessageDto {

    private String content;
    private Long senderId;
    private Long receiverId;
    private MessageType type; // JOIN, CHAT, LEAVE

}
