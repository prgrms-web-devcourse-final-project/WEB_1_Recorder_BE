package com.revup.question.controller;

import com.revup.global.dto.ApiResponse;
import com.revup.question.dto.QuestionCreateInfo;
import com.revup.question.dto.QuestionCreateRequest;
import com.revup.question.dto.QuestionIdResponse;
import com.revup.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.revup.global.dto.ApiResponse.*;

@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionIdResponse>> create(@RequestBody QuestionCreateRequest request){
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
}
