package com.revup.chat.controller;

import com.revup.annotation.SecurityUser;
import com.revup.chat.dto.ChatMessageDto;
import com.revup.chat.dto.ChatRoomGetRequest;
import com.revup.chat.usecase.CreateChatUseCase;
import com.revup.chat.usecase.GetChatRoomUseCase;
import com.revup.chat.usecase.GetMyChatRoomListUseCase;
import com.revup.global.dto.ApiResponse;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final CreateChatUseCase createChatUseCase;
    private final GetMyChatRoomListUseCase getMyChatRoomListUseCase;
    private final GetChatRoomUseCase getChatRoomUseCase;

    @PostMapping("/api/v1/chat/room")
    public Long GetChatRoom(@RequestBody ChatRoomGetRequest chatRoomGetRequest, @SecurityUser User currentUser) {
        return getChatRoomUseCase.execute(chatRoomGetRequest, currentUser);
    }

    @MessageMapping("/chat.sendMessage/{chatRoomId}") // 클라이언트가 메시지 전송
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
    @GetMapping("/api/v1/chat/room")
    public ResponseEntity<ApiResponse<List<?>>> myChatRoomList(@SecurityUser User currentUser) {
        return ResponseEntity.ok(
                ApiResponse.success(getMyChatRoomListUseCase.execute(currentUser))
        );
    }

}
