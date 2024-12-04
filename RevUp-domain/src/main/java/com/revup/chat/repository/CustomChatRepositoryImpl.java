package com.revup.chat.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revup.chat.entity.Chat;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.revup.chat.entity.QChat.chat;

@RequiredArgsConstructor
public class CustomChatRepositoryImpl implements CustomChatRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Chat> findRecentChats(User user) {
        // 사용자와 관련된 모든 채팅 파트너 ID 추출
        List<Long> partnerIds = queryFactory
                .select(
                        Expressions.numberTemplate(Long.class,
                                "case when {0} = {1} then {2} else {3} end",
                                chat.sender.id, user.getId(),
                                chat.receiver.id,
                                chat.sender.id
                        )
                )
                .from(chat)
                .where(chat.sender.eq(user).or(chat.receiver.eq(user)))
                .groupBy(
                        Expressions.numberTemplate(Long.class,
                                "case when {0} = {1} then {2} else {3} end",
                                chat.sender.id, user.getId(),
                                chat.receiver.id,
                                chat.sender.id
                        )
                )
                .fetch();

        // 각 파트너와의 가장 최근 채팅 조회
        return queryFactory
                .selectFrom(chat)
                .where(
                        (chat.sender.eq(user).and(chat.receiver.id.in(partnerIds)))
                                .or(chat.receiver.eq(user).and(chat.sender.id.in(partnerIds)))
                                .and(chat.id.in(
                                        JPAExpressions
                                                .select(chat.id.max())
                                                .from(chat)
                                                .where(
                                                        (chat.sender.eq(user).and(chat.receiver.id.in(partnerIds)))
                                                                .or(chat.receiver.eq(user).and(chat.sender.id.in(partnerIds)))
                                                )
                                                .groupBy(
                                                        Expressions.numberTemplate(Long.class,
                                                                "case when {0} = {1} then {2} else {3} end",
                                                                chat.sender.id, user.getId(),
                                                                chat.receiver.id,
                                                                chat.sender.id
                                                        )
                                                )
                                ))
                )
                .orderBy(chat.createdAt.desc())
                .fetch();
    }

}
