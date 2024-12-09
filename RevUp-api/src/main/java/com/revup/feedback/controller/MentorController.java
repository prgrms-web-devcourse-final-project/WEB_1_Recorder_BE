package com.revup.feedback.controller;

import com.revup.annotation.SecurityUser;
import com.revup.feedback.request.MentorCreateRequest;
import com.revup.feedback.request.MentorPageRequest;
import com.revup.feedback.service.response.MentorResponse;
import com.revup.feedback.usecase.CreateMentorUseCase;
import com.revup.feedback.usecase.DeleteMentorUseCase;
import com.revup.feedback.usecase.GetMentorListUseCase;
import com.revup.global.dto.ApiResponse;
import com.revup.page.Page;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mentor")
@RequiredArgsConstructor
public class MentorController {

    private final CreateMentorUseCase createMentorUseCase;
    private final GetMentorListUseCase getMentorListUseCase;
    private final DeleteMentorUseCase deleteMentorUseCase;

    /**
     * 피드백 멘토 지원
     * @param mentorCreateRequest 멘토 지원에 필요한 내용들
     * @return 생성된 멘토 id
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createMentor(@RequestBody MentorCreateRequest mentorCreateRequest,
                                                          @SecurityUser User currentUser) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        createMentorUseCase.execute(mentorCreateRequest, currentUser)
                )
        );
    }

    /**
     * 멘토 목록 페이지 조회
     * @param page 조회할 페이지 번호
     * @return 해당 페이지에 나타날 멘토 목록
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<MentorResponse>>> getMentorList(@RequestParam int page) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        getMentorListUseCase.execute(page)
                )
        );
    }

    /**
     * 멘토 지원 정보 삭제
     * @param mentorId 삭제할 멘토 아이디(유저아이디 아님)
     * @return no content
     */
    @DeleteMapping("/{mentorId}")
    public ResponseEntity<Void> deleteMentor(@PathVariable Long mentorId, @SecurityUser User currentUser) {
        deleteMentorUseCase.execute(mentorId, currentUser);
        return ResponseEntity.noContent().build();
    }

}
