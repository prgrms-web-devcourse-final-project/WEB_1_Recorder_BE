package com.revup.chat.usecase;

import com.revup.chat.dto.ChatMessageDto;
import com.revup.chat.mapper.ChatMapper;
import com.revup.chat.service.ChatService;
import com.revup.user.adaptor.UserAdaptor;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateChatUseCase {

    private final ChatMapper chatMapper;
    private final ChatService chatService;

    private final UserAdaptor userAdaptor;

    public void execute(ChatMessageDto chatMessageDto) {
        User sender = userAdaptor.findById(chatMessageDto.getSenderId());
        User receiver = userAdaptor.findById(chatMessageDto.getReceiverId());
        chatService.chatCreate(chatMapper.toEntity(chatMessageDto.getContent(), sender, receiver));
    }

}
