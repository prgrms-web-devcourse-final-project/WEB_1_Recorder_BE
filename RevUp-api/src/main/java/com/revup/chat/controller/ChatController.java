package com.revup.chat.controller;

import com.revup.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage/{sender}/{receiver}") // 클라이언트가 메시지 전송
    public void sendMessage(@DestinationVariable Long sender,
                                   @DestinationVariable Long receiver,
                                   @Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/chat/" + sender + "/" + receiver, chatMessage);
        messagingTemplate.convertAndSend("/topic/chat/" + receiver + "/" + sender, chatMessage);
    }

}
