package com.revup.feedback.controller;

import com.revup.feedback.request.MentorCreateRequest;
import com.revup.feedback.service.response.MentorResponse;
import com.revup.feedback.usecase.CreateMentorUseCase;
import com.revup.feedback.usecase.GetMentorListUseCase;
import com.revup.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mentor")
@RequiredArgsConstructor
public class MentorController {

    private final CreateMentorUseCase createMentorUseCase;
    private final GetMentorListUseCase getMentorListUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createMentor(@RequestBody MentorCreateRequest mentorCreateRequest) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        createMentorUseCase.execute(mentorCreateRequest)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MentorResponse>>> getMentorList() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        getMentorListUseCase.execute()
                )
        );
    }

}
