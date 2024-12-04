package com.revup.chat.usecase;

import com.revup.chat.service.ChatService;
import com.revup.chat.service.response.ChatResponse;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetChatListUseCase {

    private final ChatService chatService;

    public List<ChatResponse> execute(User currentUser) {
        return chatService.myChatList(currentUser);
    }

}
