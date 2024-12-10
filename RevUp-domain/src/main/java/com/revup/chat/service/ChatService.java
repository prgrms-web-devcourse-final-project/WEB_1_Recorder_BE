package com.revup.chat.service;

import com.revup.chat.entity.ChatMessage;
import com.revup.chat.entity.ChatRoom;
import com.revup.chat.entity.ChatRoomBelong;
import com.revup.chat.repository.ChatMessageRepository;
import com.revup.chat.repository.ChatRoomBelongRepository;
import com.revup.chat.repository.ChatRoomRepository;
import com.revup.chat.service.response.ChatMessageResponse;
import com.revup.chat.service.response.ChatRoomListResponse;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomBelongRepository chatRoomBelongRepository;

    @Transactional
    public void chatCreate(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    @Transactional
    public Long chatRoomGetOrCreate(User user1, User user2) {
        Optional<ChatRoom> chatRoomOptional = chatRoomBelongRepository.findChatRoomByTwoUsers(user1, user2);
        if (!chatRoomOptional.isEmpty()) return chatRoomOptional.get().getId();

        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder().build());
        ChatRoomBelong chatRoomBelong1 = ChatRoomBelong.builder()
                .chatRoom(chatRoom)
                .user(user1)
                .build();
        ChatRoomBelong chatRoomBelong2 = ChatRoomBelong.builder()
                .chatRoom(chatRoom)
                .user(user2)
                .build();

        chatRoomBelongRepository.saveAll(List.of(chatRoomBelong1, chatRoomBelong2));
        return chatRoom.getId();
    }

    public List<ChatRoomListResponse> myChatRoomList(User currentUser) {
        List<ChatRoomListResponse> responseList = new ArrayList<>();

        List<ChatRoom> chatRooms = chatRoomBelongRepository.findByUser(currentUser)
                .stream()
                .map(ChatRoomBelong::getChatRoom).toList();

        for (ChatRoom chatRoom : chatRooms) {
            ChatMessage latestMessage = chatRoomBelongRepository.findLatestMessageByChatRoom(chatRoom);
            User anotherUser = chatRoomBelongRepository.findOtherMemberInChatRoom(chatRoom, currentUser);
            if (latestMessage != null) {
                responseList.add(ChatRoomListResponse.from(latestMessage, anotherUser));
            }
        }

        return responseList;
    }

    public List<ChatMessageResponse> getChatMessagesBefore(Long chatRoomId, LocalDateTime timestamp, int size) {
        Pageable pageable = PageRequest.of(0, size);
        return chatMessageRepository.findMessagesBeforeTimeStamp(chatRoomId, timestamp, pageable).stream()
                .map(ChatMessageResponse::from).toList();
    }

}
