package com.revup.chat.controller;

import com.revup.annotation.SecurityUser;
import com.revup.chat.dto.ChatMessageDto;
import com.revup.chat.dto.ChatRoomGetRequest;
import com.revup.chat.service.response.ChatMessageResponse;
import com.revup.chat.service.response.ChatRoomListResponse;
import com.revup.chat.usecase.CreateChatUseCase;
import com.revup.chat.usecase.GetChatMessagesUseCase;
import com.revup.chat.usecase.GetChatRoomUseCase;
import com.revup.chat.usecase.GetMyChatRoomListUseCase;
import com.revup.global.dto.ApiResponse;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final CreateChatUseCase createChatUseCase;
    private final GetMyChatRoomListUseCase getMyChatRoomListUseCase;
    private final GetChatRoomUseCase getChatRoomUseCase;
    private final GetChatMessagesUseCase getChatMessagesUseCase;

    /**
     * 채팅방 생성 api
     * @param chatRoomGetRequest 채팅상대 id
     * @param currentUser 요청한 사람
     * @return 둘 사이의 채팅방이 이미 있으면 그거 id 반환, 없으면 만들어서 반환
     */
    @PostMapping("/api/v1/chat/room")
    public ResponseEntity<ApiResponse<Long>> GetChatRoom(@RequestBody ChatRoomGetRequest chatRoomGetRequest, @SecurityUser User currentUser) {
        return ResponseEntity.ok(
                ApiResponse.success(getChatRoomUseCase.execute(chatRoomGetRequest, currentUser))
        );
    }

    /**
     * 채팅 메시지 전송 처리
     * @param chatRoomId 메시지를 받게 되는 채팅방 id
     * @param chatMessageDto 메시지 전송 dto
     * 구독한 클라이언트들에 전송하고, DB에 채팅을 저장함
     */
    @MessageMapping("/chat.sendMessage/{chatRoomId}")
    public void sendMessage(@DestinationVariable Long chatRoomId,
                            @Payload ChatMessageDto chatMessageDto
    ) {
        messagingTemplate.convertAndSend("/topic/chat/" + chatRoomId, chatMessageDto);
        createChatUseCase.execute(chatMessageDto, chatRoomId);
    }

    /**
     * 채팅방 리스트 반환 api
     * 채팅한 적 있는 사람들 각각의 가장 최근 채팅 하나씩을 목록으로 반환함
     * @param currentUser 인증 헤더를 통해 자동으로 현재 로그인 사용자 주입됨
     * @return 각자의 마지막 채팅 목록
     */
    @GetMapping("/api/v1/chat/room/my")
    public ResponseEntity<ApiResponse<List<ChatRoomListResponse>>> myChatRoomList(@SecurityUser User currentUser) {
        return ResponseEntity.ok(
                ApiResponse.success(getMyChatRoomListUseCase.execute(currentUser))
        );
    }

    /**
     * 채팅 내역 조회 api
     * @param chatRoomId 조회할 채팅방 id
     * @param lastMessageTimestamp 무한스크롤 지원을 위해 기준점이 될 타임스탬프
     * @param size 한 번에 최대로 로드할 채팅 내역 개수. 기본 10
     * @return 최대 size 개의 채팅 내역
     */
    @GetMapping("/api/v1/chat/{chatRoomId}/messages")
    public ResponseEntity<ApiResponse<List<ChatMessageResponse>>> getChatMessages(
            @PathVariable Long chatRoomId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastMessageTimestamp,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        getChatMessagesUseCase.execute(chatRoomId, lastMessageTimestamp, size)
                )
        );
    }

}
