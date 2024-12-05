package com.revup.chat.adaptor;

import com.revup.chat.entity.ChatRoom;
import com.revup.chat.repository.ChatRoomRepository;
import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatRoomAdaptor {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom findById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new AppException(ErrorCode.CHAT_ROOM_NOT_FOUND, chatRoomId));
    }

}
