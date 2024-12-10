package com.revup.chat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatMessageDto {

    private Long senderId;

    @NotBlank
    private String content;

    private String authorization;

    private MessageType type; // JOIN, CHAT, LEAVE

}
