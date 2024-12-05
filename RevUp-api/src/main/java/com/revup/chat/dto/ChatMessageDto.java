package com.revup.chat.dto;

import lombok.Data;

@Data
public class ChatMessageDto {

    private String content;
    private String authorization;
    private MessageType type; // JOIN, CHAT, LEAVE

}
