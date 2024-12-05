package com.revup.chat.repository;

import com.revup.chat.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("""
        SELECT m 
        FROM ChatMessage m
        WHERE m.chatRoomBelong.chatRoom.id = :chatRoomId
        AND m.createdAt < :timeStamp
        ORDER BY m.createdAt DESC
    """)
    List<ChatMessage> findMessagesBeforeTimeStamp(
            @Param("chatRoomId") Long chatRoomId,
            @Param("timeStamp") LocalDateTime timestamp,
            Pageable pageable
    );

}
