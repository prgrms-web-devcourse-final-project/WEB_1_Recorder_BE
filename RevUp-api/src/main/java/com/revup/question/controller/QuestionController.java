package com.revup.question.controller;

import com.revup.global.dto.ApiResponse;
import com.revup.page.Page;
import com.revup.question.dto.request.QuestionCreateRequest;
import com.revup.question.dto.request.QuestionPageRequest;
import com.revup.question.dto.response.QuestionBriefResponse;
import com.revup.question.dto.response.QuestionDetailsResponse;
import com.revup.question.dto.response.QuestionIdResponse;
import com.revup.question.usecase.CreateQuestionUseCase;
import com.revup.question.usecase.GetQuestionDetailsUseCase;
import com.revup.question.usecase.GetQuestionListUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.revup.global.dto.ApiResponse.success;

@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {
    private final CreateQuestionUseCase createQuestionUseCase;
    private final GetQuestionListUseCase getQuestionListUseCase;
    private final GetQuestionDetailsUseCase getQuestionDetailsUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionIdResponse>> create(@Valid @RequestBody QuestionCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(success(createQuestionUseCase.execute(request)));

    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Page<QuestionBriefResponse>>> getQuestionList(@Valid QuestionPageRequest request) {
        return ResponseEntity.ok().body(success(getQuestionListUseCase.execute(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<QuestionDetailsResponse>> getQuestionDetails(@RequestParam Long id){
        return ResponseEntity.ok().body(success(getQuestionDetailsUseCase.execute(id)));
    }


}
