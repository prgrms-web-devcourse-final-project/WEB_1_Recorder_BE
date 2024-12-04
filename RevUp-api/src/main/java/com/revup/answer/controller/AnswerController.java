package com.revup.answer.controller;

import com.revup.annotation.SecurityUser;
import com.revup.answer.dto.request.AnswerCreateRequest;
import com.revup.answer.dto.request.AnswerUpdateRequest;
import com.revup.answer.dto.response.AnswerDetailsResponse;
import com.revup.answer.dto.response.AnswerIdResponse;
import com.revup.answer.usecase.*;
import com.revup.global.dto.ApiResponse;
import com.revup.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.revup.global.util.ResponseUtil.success;

@RestController
@RequestMapping("/api/v1/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final CreateAnswerUseCase createAnswerUseCase;
    private final GetAnswerListUseCase getAnswerListUseCase;
    private final GetMyAnswersUseCase getMyAnswersUseCase;
    private final UpdateAnswerUseCase updateAnswerUseCase;
    private final DeleteAnswerUseCase deleteAnswerUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<AnswerIdResponse>> create(@Valid @RequestBody AnswerCreateRequest request,
                                                                @SecurityUser User currentUser) {
        return success(HttpStatus.CREATED, createAnswerUseCase.execute(request, currentUser));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<AnswerIdResponse>> update(@Valid @RequestBody AnswerUpdateRequest request,
                                                                @SecurityUser User currentUser
    ){
        return success(updateAnswerUseCase.execute(request, currentUser));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AnswerDetailsResponse>> getByQuestionId(@RequestParam Long questionId){
        return success(getAnswerListUseCase.execute(questionId));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id,
                                       @SecurityUser User currentUser){
        deleteAnswerUseCase.execute(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
