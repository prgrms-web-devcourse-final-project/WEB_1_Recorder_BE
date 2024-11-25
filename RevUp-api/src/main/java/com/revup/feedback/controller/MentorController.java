package com.revup.feedback.controller;

import com.revup.feedback.request.MentorCreateRequest;
import com.revup.feedback.usecase.CreateMentorUseCase;
import com.revup.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mentor")
@RequiredArgsConstructor
public class MentorController {

    private final CreateMentorUseCase createMentorUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createMentor(@RequestBody MentorCreateRequest mentorCreateRequest) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        createMentorUseCase.execute(mentorCreateRequest)
                )
        );
    }

}
