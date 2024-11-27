package com.revup.feedback.controller;

import com.revup.feedback.FeedbackWebSocketHandler;
import com.revup.feedback.usecase.CreateFeedbackCodeUseCase;
import com.revup.feedback.usecase.GetFeedbackCodeUseCase;
import com.revup.feedback.usecase.UpdateFeedbackCodeUseCase;
import com.revup.feedback.request.FeedbackCodeCreateRequest;
import com.revup.feedback.request.FeedbackCodeUpdateRequest;
import com.revup.feedback.service.response.FeedbackCodeResponse;
import com.revup.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feedback-code")
public class FeedbackCodeController {

    private final FeedbackWebSocketHandler feedbackWebSocketHandler;
    private final UpdateFeedbackCodeUseCase updateFeedbackCodeUseCase;
    private final CreateFeedbackCodeUseCase createFeedbackCodeUseCase;
    private final GetFeedbackCodeUseCase getFeedbackCodeUseCase;

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
                ApiResponse.success(getFeedbackCodeUseCase.execute(feedbackCodeId))
        );
    }

    /**
     * 피드백코드 자동저장 요청
     * @param feedbackCodeId 자동저장할 피드백코드의 id
     * @param feedbackCodeUpdateRequest 최신화되는 코드 내용을 담은 dto
     * @return 최신화된 피드백코드 id
     */
    @PatchMapping("/auto/{feedbackCodeId}")
    public ResponseEntity<ApiResponse<Long>> autoUpdateFeedbackCode(@PathVariable Long feedbackCodeId, @RequestBody FeedbackCodeUpdateRequest feedbackCodeUpdateRequest) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        updateFeedbackCodeUseCase.execute(feedbackCodeId, feedbackCodeUpdateRequest)
                )
        );
    }

    /**
     * 특정 피드백에 피드백코드 추가
     * @param feedbackCodeCreateRequest (피드백 id, 추가할 코드 파일명, 추가할 코드 내용)
     * @return 추가된 피드백코드 id
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createFeedbackCode(@RequestBody FeedbackCodeCreateRequest feedbackCodeCreateRequest) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        createFeedbackCodeUseCase.execute(feedbackCodeCreateRequest)
                )
        );
    }

}
