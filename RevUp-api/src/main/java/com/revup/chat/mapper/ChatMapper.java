package com.revup.chat.mapper;

import com.revup.chat.entity.ChatMessage;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMapper {

    public ChatMessage toEntity(String content) {
        return ChatMessage.builder()
                .content(content)
                .build();
    }

}
