package com.revup.chat.usecase;

import com.revup.chat.service.ChatService;
import com.revup.chat.service.response.ChatRoomListResponse;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetMyChatRoomListUseCase {

    private final ChatService chatService;

    public List<ChatRoomListResponse> execute(User currentUser) {
        return chatService.myChatRoomList(currentUser);
    }

}
