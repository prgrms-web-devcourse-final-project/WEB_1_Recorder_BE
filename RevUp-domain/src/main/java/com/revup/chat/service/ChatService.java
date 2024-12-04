package com.revup.chat.service;

import com.revup.chat.entity.Chat;
import com.revup.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    @Transactional
    public void chatCreate(Chat chat) {
        chatRepository.save(chat);
    }

}
