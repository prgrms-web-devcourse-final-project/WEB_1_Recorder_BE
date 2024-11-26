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

    /**
     * 피드백 멘토 지원
     * @param mentorCreateRequest 멘토 지원에 필요한 내용들
     * @return 생성된 멘토 id
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createMentor(@RequestBody MentorCreateRequest mentorCreateRequest) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        createMentorUseCase.execute(mentorCreateRequest)
                )
        );
    }

    /**
     * 피드백 멘토 전체 목록 조회
     * TODO: 페이징 처리
     * @return 전체 멘토 목록
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<MentorResponse>>> getMentorList() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        getMentorListUseCase.execute()
                )
        );
    }

}
