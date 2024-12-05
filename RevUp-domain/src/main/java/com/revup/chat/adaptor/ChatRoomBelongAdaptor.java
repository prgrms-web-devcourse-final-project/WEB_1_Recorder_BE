package com.revup.chat.adaptor;

import com.revup.chat.entity.ChatRoom;
import com.revup.chat.entity.ChatRoomBelong;
import com.revup.chat.repository.ChatRoomBelongRepository;
import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatRoomBelongAdaptor {

    private final ChatRoomBelongRepository chatRoomBelongRepository;

    public ChatRoomBelong findByUserAndChatRoom(User user, ChatRoom chatRoom) {
        return chatRoomBelongRepository.findByUserAndChatRoom(user, chatRoom).orElseThrow(() -> new AppException(ErrorCode.CHAT_ROOM_NOT_FOUND));
    }

}
