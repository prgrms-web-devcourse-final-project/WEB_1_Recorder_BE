package com.revup.feedback.controller;

import com.revup.feedback.request.FeedbackCreateRequest;
import com.revup.feedback.service.response.FeedbackResponse;
import com.revup.feedback.usecase.CreateFeedbackUseCase;
import com.revup.feedback.usecase.GetSubmittedFeedbackListUseCase;
import com.revup.feedback.usecase.GetWaitingFeedbackListUseCase;
import com.revup.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final CreateFeedbackUseCase createFeedbackUseCase;
    private final GetWaitingFeedbackListUseCase getWaitingFeedbackListUseCase;
    private final GetSubmittedFeedbackListUseCase getSubmittedFeedbackListUseCase;

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

    /**
     * 자기한테 신청되어서 대기 중인 피드백 목록 조회
     * @return 해당 목록
     */
    @GetMapping("/submitted/waiting")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getSubmittedWaitingFeedbackList() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        getWaitingFeedbackListUseCase.execute()
                )
        );
    }

    /**
     * 자기한테 신청된 피드백 목록 조회
     * @return 해당 목록
     */
    @GetMapping("/submitted")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getSubmittedFeedbackList() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        getSubmittedFeedbackListUseCase.execute()
                )
        );
    }

}
