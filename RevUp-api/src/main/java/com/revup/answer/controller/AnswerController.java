package com.revup.answer.controller;

import com.revup.answer.dto.request.AnswerCreateRequest;
import com.revup.answer.dto.request.AnswerUpdateRequest;
import com.revup.answer.dto.response.AnswerIdResponse;
import com.revup.answer.usecase.CreateAnswerUseCase;
import com.revup.answer.usecase.DeleteAnswerUseCase;
import com.revup.answer.usecase.GetMyAnswersUseCase;
import com.revup.answer.usecase.UpdateAnswerUseCase;
import com.revup.global.dto.ApiResponse;
import com.revup.user.entity.User;
import com.revup.user.util.UserUtil;
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
    private final GetMyAnswersUseCase getMyAnswersUseCase;
    private final UpdateAnswerUseCase updateAnswerUseCase;
    private final DeleteAnswerUseCase deleteAnswerUseCase;
    private final UserUtil userUtil;

    @PostMapping
    public ResponseEntity<ApiResponse<AnswerIdResponse>> create(@Valid @RequestBody AnswerCreateRequest request) {
        User currentUser = userUtil.getCurrentUser();
        return success(HttpStatus.CREATED, createAnswerUseCase.execute(request, currentUser));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<AnswerIdResponse>> update(@Valid @RequestBody AnswerUpdateRequest request){
        User currentUser = userUtil.getCurrentUser();
        return success(updateAnswerUseCase.execute(request, currentUser));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id){
        User currentUser = userUtil.getCurrentUser();
        deleteAnswerUseCase.execute(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
