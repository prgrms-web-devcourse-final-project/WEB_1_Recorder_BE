package com.revup.chat.entity;

import com.revup.common.BaseTimeEntity;
import com.revup.common.BooleanStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "chat_message_read")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageRead extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_message_id")
    private ChatMessage chatMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_belong_id")
    private ChatRoomBelong chatRoomBelong;

    @Enumerated(EnumType.STRING)
    private BooleanStatus isRead = BooleanStatus.FALSE;

    @Builder
    private ChatMessageRead(ChatMessage chatMessage, ChatRoomBelong chatRoomBelong) {
        this.chatMessage = chatMessage;
        this.chatRoomBelong = chatRoomBelong;
    }

}
