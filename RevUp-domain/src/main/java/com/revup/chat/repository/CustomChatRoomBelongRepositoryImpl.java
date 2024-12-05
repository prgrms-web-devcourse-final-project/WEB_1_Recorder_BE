package com.revup.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revup.chat.entity.ChatMessage;
import com.revup.chat.entity.ChatRoom;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;

import static com.revup.chat.entity.QChatMessage.chatMessage;
import static com.revup.chat.entity.QChatRoomBelong.chatRoomBelong;

@RequiredArgsConstructor
public class CustomChatRoomBelongRepositoryImpl implements CustomChatRoomBelongRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public ChatMessage findLatestMessageByChatRoom(ChatRoom chatRoom) {
        return queryFactory
                .selectFrom(chatMessage)
                .where(chatMessage.chatRoomBelong.chatRoom.eq(chatRoom))
                .orderBy(chatMessage.createdAt.desc())
                .fetchFirst();
    }

    @Override
    public User findOtherMemberInChatRoom(ChatRoom chatRoom, User currentUser) {
        return queryFactory
                .select(chatRoomBelong.user)
                .from(chatRoomBelong)
                .where(chatRoomBelong.chatRoom.eq(chatRoom)
                        .and(chatRoomBelong.user.ne(currentUser)))
                .fetchOne();
    }

}
