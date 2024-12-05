package com.revup.chat.usecase;

import com.revup.chat.service.ChatService;
import com.revup.chat.service.response.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class GetChatMessagesUseCase {

    private final ChatService chatService;

    public List<ChatMessageResponse> execute(Long chatRoomId, LocalDateTime lastMessageTimestamp, int size) {
        return chatService.getChatMessagesBefore(chatRoomId, Objects.requireNonNullElseGet(lastMessageTimestamp, LocalDateTime::now), size);
    }

}
