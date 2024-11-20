package com.revup.question.controller;

import com.revup.global.dto.ApiResponse;
import com.revup.question.dto.*;
import com.revup.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.revup.global.dto.ApiResponse.success;

@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionIdResponse>> create(@RequestBody QuestionCreateRequest request) {
        QuestionIdResponse questionIdResponse = questionService.create(QuestionCreateInfo.of(
                request.title(),
                request.content(),
                request.type(),
                request.isAnonymous(),
                request.state(),
                request.categories(),
                request.imageUrls()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(success(questionIdResponse));

    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<QuestionBriefResponse>>> getQuestionList(@ModelAttribute QuestionPageRequest request) {
        List<QuestionBriefResponse> responseList = questionService.getQuestionList(QuestionPageInfo.of(
                request.type(),
                request.page(),
                request.size()
        ));
        return ResponseEntity.ok().body(success(responseList));
    }


}
