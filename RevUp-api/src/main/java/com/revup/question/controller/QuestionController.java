package com.revup.question.controller;

import com.revup.global.dto.ApiResponse;
import com.revup.page.Page;
import com.revup.question.dto.QuestionUpdateRequest;
import com.revup.question.dto.request.QuestionCreateRequest;
import com.revup.question.dto.request.QuestionPageRequest;
import com.revup.question.dto.response.QuestionBriefResponse;
import com.revup.question.dto.response.QuestionDetailsResponse;
import com.revup.question.dto.response.QuestionIdResponse;
import com.revup.question.usecase.*;
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
    private final QuestionUseCases questionUseCases;

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionIdResponse>> create(@Valid @RequestBody QuestionCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(success(questionUseCases.create(request)));

    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Page<QuestionBriefResponse>>> getQuestionList(@Valid QuestionPageRequest request) {
        return ResponseEntity.ok().body(success(questionUseCases.getList(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<QuestionDetailsResponse>> getQuestionDetails(@RequestParam Long id){
        return ResponseEntity.ok().body(success(questionUseCases.getDetails(id)));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<QuestionIdResponse>> update(@Valid @RequestBody QuestionUpdateRequest request){
        return ResponseEntity.ok().body(success((questionUseCases.update(request))));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        questionUseCases.delete(id);
        return ResponseEntity.noContent().build();
    }

}
