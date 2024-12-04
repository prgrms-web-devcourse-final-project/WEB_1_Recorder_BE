package com.revup.chat.service;

import com.revup.chat.entity.Chat;
import com.revup.chat.repository.ChatRepository;
import com.revup.chat.service.response.ChatResponse;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    @Transactional
    public void chatCreate(Chat chat) {
        chatRepository.save(chat);
    }

    public List<ChatResponse> myChatList(User currentUser) {
        return chatRepository.findRecentChats(currentUser)
                .stream()
                .map(ChatResponse::from).toList();
    }

}
