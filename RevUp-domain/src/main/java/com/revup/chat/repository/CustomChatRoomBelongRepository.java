package com.revup.chat.repository;

import com.revup.chat.entity.ChatMessage;
import com.revup.chat.entity.ChatRoom;
import com.revup.user.entity.User;

public interface CustomChatRoomBelongRepository {

    ChatMessage findLatestMessageByChatRoom(ChatRoom chatRoom);

    User findOtherMemberInChatRoom(ChatRoom chatRoom, User currentUser);

}
