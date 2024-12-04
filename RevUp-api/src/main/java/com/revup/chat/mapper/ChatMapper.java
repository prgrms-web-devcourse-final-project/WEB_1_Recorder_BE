package com.revup.chat.mapper;

import com.revup.chat.entity.Chat;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMapper {

    public Chat toEntity(String content, User sender, User receiver) {
        return Chat.builder()
                .content(content)
                .sender(sender)
                .receiver(receiver)
                .build();
    }

}
