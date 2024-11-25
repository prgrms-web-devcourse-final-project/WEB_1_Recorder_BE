package com.revup.answer.controller;

import com.revup.answer.dto.request.AnswerCreateRequest;
import com.revup.answer.dto.response.AnswerIdResponse;
import com.revup.answer.usecase.CreateAnswerUseCase;
import com.revup.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.revup.global.dto.ApiResponse.success;

@RestController
@RequestMapping("/api/v1/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final CreateAnswerUseCase createAnswerUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<AnswerIdResponse>> create(@Valid @RequestBody AnswerCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(success(createAnswerUseCase.execute(request)));
    }
}
