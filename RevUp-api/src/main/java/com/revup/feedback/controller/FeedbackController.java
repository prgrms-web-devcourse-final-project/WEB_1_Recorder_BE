package com.revup.feedback.controller;

import com.revup.annotation.SecurityUser;
import com.revup.feedback.request.FeedbackAcceptRequest;
import com.revup.feedback.request.FeedbackCreateRequest;
import com.revup.feedback.service.response.FeedbackDetailsResponse;
import com.revup.feedback.service.response.FeedbackListResponse;
import com.revup.feedback.usecase.*;
import com.revup.global.dto.ApiResponse;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final CreateFeedbackUseCase createFeedbackUseCase;
    private final GetFeedbackUseCase getFeedbackUseCase;
    private final GetWaitingFeedbackListUseCase getWaitingFeedbackListUseCase;
    private final GetSubmittedFeedbackListUseCase getSubmittedFeedbackListUseCase;
    private final AcceptFeedbackUseCase acceptFeedbackUseCase;
    private final GetAcceptedFeedbackListUseCase getAcceptedFeedbackListUseCase;

    /**
     * 피드백 생성 메서드
     * @param feedbackCreateRequest 피드백 생성에 필요한 내용들
     * @return 생성된 피드백 id
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createFeedback(@RequestBody FeedbackCreateRequest feedbackCreateRequest,
                                                            @SecurityUser User currentUser) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        createFeedbackUseCase.execute(feedbackCreateRequest, currentUser)
                )
        );
    }

    /**
     * 자기한테 신청되어서 대기 중인 피드백 목록 조회
     * @return 해당 목록
     */
    @GetMapping("/submitted/waiting")
    public ResponseEntity<ApiResponse<List<FeedbackListResponse>>> getSubmittedWaitingFeedbackList(@SecurityUser User currentUser) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        getWaitingFeedbackListUseCase.execute(currentUser)
                )
        );
    }

    /**
     * 자기한테 신청된 피드백 목록 조회
     * @return 해당 목록
     */
    @GetMapping("/submitted")
    public ResponseEntity<ApiResponse<List<FeedbackListResponse>>> getSubmittedFeedbackList(@SecurityUser User currentUser) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        getSubmittedFeedbackListUseCase.execute(currentUser)
                )
        );
    }

    /**
     * 자기한테 신청된 피드백 승인하기
     * @param feedbackAcceptRequest 승인할 피드백 id
     * @return 승인된 피드백 id
     */
    @PostMapping("/submitted/accepted")
    public ResponseEntity<ApiResponse<Long>> acceptFeedback(@RequestBody FeedbackAcceptRequest feedbackAcceptRequest,
                                                            @SecurityUser User currentUser) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        acceptFeedbackUseCase.execute(feedbackAcceptRequest,currentUser)
                )
        );
    }

    /**
     * 자기한테 신청되어서 승인된 피드백 목록 조회
     * @return 해당 목록
     */
    @GetMapping("/submitted/accepted")
    public ResponseEntity<ApiResponse<List<FeedbackListResponse>>> getSubmittedAcceptedFeedbackList(@SecurityUser User currentUser) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        getAcceptedFeedbackListUseCase.execute(currentUser)
                )
        );
    }


    /**
     * 피드백 상세 조회
     * @param feedbackId 조회할 피드백 아이디
     * @return 해당 피드백 상세정보
     */
    @GetMapping("/{feedbackId}")
    public ResponseEntity<ApiResponse<FeedbackDetailsResponse>> getFeedbackDetails(@PathVariable Long feedbackId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        getFeedbackUseCase.execute(feedbackId)
                )
        );
    }

}
