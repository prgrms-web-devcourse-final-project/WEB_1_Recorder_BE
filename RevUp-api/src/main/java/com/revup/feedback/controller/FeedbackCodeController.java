package com.revup.feedback.controller;

import com.revup.feedback.FeedbackWebSocketHandler;
import com.revup.feedback.controller.request.FeedbackCodeUpdateRequest;
import com.revup.feedback.service.FeedbackCodeService;
import com.revup.feedback.service.response.FeedbackCodeResponse;
import com.revup.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback-code")
public class FeedbackCodeController {

    private final FeedbackWebSocketHandler feedbackWebSocketHandler;
    private final FeedbackCodeService feedbackCodeService;

    /**
     * 특정 피드백코드의 라이브 세션에 연결된 사람 수 반환
     * @param feedbackCodeId 세션 수를 조회할 피드백코드 id
     * @return 세션 개수
     */
    @GetMapping("/sessions/{feedbackCodeId}")
    public ResponseEntity<ApiResponse<Integer>> getSessionsNumber(@PathVariable Long feedbackCodeId) {
        return ResponseEntity.ok(
                ApiResponse.success(feedbackWebSocketHandler.getActiveSessions(feedbackCodeId))
        );
    }

    /**
     * 피드백코드 조회 요청
     * @param feedbackCodeId 조회할 피드백코드 id
     * @return (파일명, 내용)
     */
    @GetMapping("/{feedbackCodeId}")
    public ResponseEntity<ApiResponse<FeedbackCodeResponse>> getFeedbackCode(@PathVariable Long feedbackCodeId) {
        return ResponseEntity.ok(
                ApiResponse.success(feedbackCodeService.getFeedbackCode(feedbackCodeId))
        );
    }

    /**
     * 피드백코드 자동저장 요청
     * @param feedbackCodeId
     * @param feedbackCodeUpdateRequest
     * @return
     */
    @PatchMapping("/auto/{feedbackCodeId}")
    public ResponseEntity<?> autoUpdateFeedbackCode(@PathVariable Long feedbackCodeId, @RequestBody FeedbackCodeUpdateRequest feedbackCodeUpdateRequest) {
        return ResponseEntity.ok(
                ApiResponse.success(feedbackCodeService.feedbackCodeUpdate(feedbackCodeId, feedbackCodeUpdateRequest.getContent()))
        );
    }

}
