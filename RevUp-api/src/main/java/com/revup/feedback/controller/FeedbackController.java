package com.revup.feedback.controller;

import com.revup.feedback.controller.request.FeedbackCreateRequest;
import com.revup.feedback.controller.command.FeedbackCommand;
import com.revup.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackCommand feedbackCommand;

    /**
     * 피드백 생성 메서드
     * @param feedbackCreateRequest 피드백 생성에 필요한 내용들
     * @return 생성된 피드백 id
     */
    @PostMapping
    public ResponseEntity<?> createFeedback(@RequestBody FeedbackCreateRequest feedbackCreateRequest) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        feedbackCommand.feedbackCreateCommand(feedbackCreateRequest)
                )
        );
    }

}
