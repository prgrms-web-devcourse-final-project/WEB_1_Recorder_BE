package com.revup.question.controller;

import com.revup.annotation.SecurityUser;
import com.revup.global.dto.ApiResponse;
import com.revup.page.Page;
import com.revup.question.dto.request.QuestionAcceptAnswerRequest;
import com.revup.question.dto.request.QuestionCreateRequest;
import com.revup.question.dto.request.QuestionPageRequest;
import com.revup.question.dto.request.QuestionUpdateRequest;
import com.revup.question.dto.response.QuestionBriefResponse;
import com.revup.question.dto.response.QuestionDetailsResponse;
import com.revup.question.dto.response.QuestionIdResponse;
import com.revup.question.usecase.*;
import com.revup.user.entity.User;
import com.revup.user.util.UserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.revup.global.util.ResponseUtil.success;

@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {
    private final CreateQuestionUseCase createQuestionUseCase;
    private final UpdateQuestionUseCase updateQuestionUseCase;
    private final DeleteQuestionUseCase deleteQuestionUseCase;
    private final GetQuestionListUseCase getQuestionListUseCase;
    private final GetQuestionDetailsUseCase getQuestionDetailsUseCase;
    private final GetMyQuestionsUseCase getMyQuestionsUseCase;
    private final GetSpecificQuestionsUseCase getSpecificQuestionsUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionIdResponse>> create(@Valid @RequestBody QuestionCreateRequest request,
                                                                  @SecurityUser User currentUser) {
        return success(HttpStatus.CREATED, createQuestionUseCase.execute(request, currentUser));

    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Page<QuestionBriefResponse>>> getQuestionList(@Valid QuestionPageRequest request) {
        return success(getQuestionListUseCase.execute(request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<QuestionDetailsResponse>> getQuestionDetails(@RequestParam Long id) {
        return success(getQuestionDetailsUseCase.execute(id));

    }

    @GetMapping("/popular")
    public ResponseEntity<ApiResponse<List<QuestionBriefResponse>>> getPopularQuestions(@RequestParam int size,
                                                                                        @RequestParam int days) {
        return success(getSpecificQuestionsUseCase.getPopulars(size, days));
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse<List<QuestionBriefResponse>>> getRecentQuestions(@RequestParam int size){
        return success(getSpecificQuestionsUseCase.getRecent(size));
    }

    @GetMapping("/stack")
    public ResponseEntity<ApiResponse<List<QuestionBriefResponse>>> getStackQuestions(@RequestParam int size,
                                                                                      @RequestParam String stack){
        return success(getSpecificQuestionsUseCase.getByStack(size, stack));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<QuestionBriefResponse>>> getMyQuestionList(@RequestParam(required = false) Long lastId,
                                                                                      @RequestParam int size,
                                                                                      @SecurityUser User currentUser) {
        return success(getMyQuestionsUseCase.execute(lastId, size, currentUser));
    }

    @PatchMapping("/accept")
    public ResponseEntity<ApiResponse<QuestionIdResponse>> acceptAnswer(@RequestBody QuestionAcceptAnswerRequest request,
                                                                        @SecurityUser User currentUser){
        return success(updateQuestionUseCase.acceptAnswer(request, currentUser));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<QuestionIdResponse>> update(@Valid @RequestBody QuestionUpdateRequest request,
                                                                  @SecurityUser User currentUser){
        return success((updateQuestionUseCase.updateQuestion(request, currentUser)));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id,
                                       @SecurityUser User currentUser) {
        deleteQuestionUseCase.execute(id, currentUser);
        return ResponseEntity.noContent().build();
    }

}
