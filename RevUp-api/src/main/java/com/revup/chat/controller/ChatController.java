package com.revup.chat.controller;

import com.revup.chat.dto.ChatMessageDto;
import com.revup.chat.usecase.CreateChatUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final CreateChatUseCase createChatUseCase;

    @MessageMapping("/chat.sendMessage/{sender}/{receiver}") // 클라이언트가 메시지 전송
    public void sendMessage(@DestinationVariable Long sender,
                            @DestinationVariable Long receiver,
                            @Payload ChatMessageDto chatMessageDto
    ) {
        messagingTemplate.convertAndSend("/topic/chat/" + sender + "/" + receiver, chatMessageDto);
        messagingTemplate.convertAndSend("/topic/chat/" + receiver + "/" + sender, chatMessageDto);

        createChatUseCase.execute(chatMessageDto);
    }

}
