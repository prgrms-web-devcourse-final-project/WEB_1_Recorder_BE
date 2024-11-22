package com.revup.question.controller;

import com.revup.global.dto.ApiResponse;
import com.revup.page.Page;
import com.revup.question.dto.request.QuestionCreateRequest;
import com.revup.question.dto.request.QuestionPageRequest;
import com.revup.question.dto.response.QuestionBriefResponse;
import com.revup.question.dto.response.QuestionIdResponse;
import com.revup.question.usecase.QuestionCommand;
import com.revup.question.usecase.QuestionQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.revup.global.dto.ApiResponse.success;

@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionCommand questionCommand;
    private final QuestionQuery questionQuery;

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionIdResponse>> create(@RequestBody QuestionCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(success(questionCommand.create(request)));

    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Page<QuestionBriefResponse>>> getQuestionList(QuestionPageRequest request) {
        return ResponseEntity.ok().body(success(questionQuery.getQuestionList(request)));
    }


}
