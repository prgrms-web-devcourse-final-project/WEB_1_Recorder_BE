package com.revup.feedback.controller;

import com.revup.feedback.request.FeedbackCreateRequest;
import com.revup.feedback.usecase.CreateFeedbackUseCase;
import com.revup.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final CreateFeedbackUseCase createFeedbackUseCase;

    /**
     * 피드백 생성 메서드
     * @param feedbackCreateRequest 피드백 생성에 필요한 내용들
     * @return 생성된 피드백 id
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createFeedback(@RequestBody FeedbackCreateRequest feedbackCreateRequest) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        createFeedbackUseCase.execute(feedbackCreateRequest)
                )
        );
    }

}
