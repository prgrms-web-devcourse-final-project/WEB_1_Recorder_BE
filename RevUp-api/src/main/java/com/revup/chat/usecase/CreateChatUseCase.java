package com.revup.chat.usecase;

import com.revup.chat.adaptor.ChatRoomAdaptor;
import com.revup.chat.adaptor.ChatRoomBelongAdaptor;
import com.revup.chat.dto.ChatMessageDto;
import com.revup.chat.entity.ChatMessage;
import com.revup.chat.entity.ChatRoom;
import com.revup.chat.entity.ChatRoomBelong;
import com.revup.chat.mapper.ChatMapper;
import com.revup.chat.service.ChatService;
import com.revup.jwt.RevUpJwtProvider;
import com.revup.user.adaptor.UserAdaptor;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateChatUseCase {

    private final ChatMapper chatMapper;
    private final ChatService chatService;

    private final ChatRoomAdaptor chatRoomAdaptor;
    private final ChatRoomBelongAdaptor chatRoomBelongAdaptor;
    private final UserAdaptor userAdaptor;

    private final RevUpJwtProvider jwtProvider;

    public void execute(ChatMessageDto chatMessageDto, Long chatRoomId) {
        Long userId = jwtProvider.getTokenUserPrincipal(chatMessageDto.getAuthorization().substring(7)).id();
        User sender = userAdaptor.findById(userId);

        ChatRoom chatRoom = chatRoomAdaptor.findById(chatRoomId);
        ChatRoomBelong chatRoomBelong = chatRoomBelongAdaptor.findByUserAndChatRoom(sender, chatRoom);

        ChatMessage chatMessage = chatMapper.toEntity(chatMessageDto.getContent(), chatRoomBelong);

        chatService.chatCreate(chatMessage);
    }

}
