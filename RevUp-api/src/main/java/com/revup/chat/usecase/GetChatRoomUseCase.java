package com.revup.chat.usecase;

import com.revup.chat.dto.ChatRoomGetRequest;
import com.revup.chat.service.ChatService;
import com.revup.user.adaptor.UserAdaptor;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetChatRoomUseCase {

    private final ChatService chatService;
    private final UserAdaptor userAdaptor;

    public Long execute(ChatRoomGetRequest chatRoomGetRequest, User currentUser) {
        User opponent = userAdaptor.findById(chatRoomGetRequest.getOpponentId());
        return chatService.chatRoomGetOrCreate(currentUser, opponent);
    }

}
