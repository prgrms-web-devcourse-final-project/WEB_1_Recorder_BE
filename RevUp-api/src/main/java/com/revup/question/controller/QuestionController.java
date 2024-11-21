package com.revup.question.controller;

import com.revup.global.dto.ApiResponse;
import com.revup.question.usecase.CreateQuestionUseCase;
import com.revup.question.dto.request.QuestionCreateRequest;
import com.revup.question.dto.response.QuestionIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.revup.global.dto.ApiResponse.success;

@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {
    private final CreateQuestionUseCase createQuestionUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionIdResponse>> create(@RequestBody QuestionCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(success(createQuestionUseCase.create(request)));

    }

//    @GetMapping("/list")
//    public ResponseEntity<ApiResponse<List<QuestionBriefResponse>>> getQuestionList(@ModelAttribute QuestionPageRequest request) {
//        List<QuestionBriefResponse> responseList = questionService.getQuestionList(QuestionPageInfo.of(
//                request.type(),
//                request.page(),
//                request.size()
//        ));
//        return ResponseEntity.ok().body(success(responseList));
//    }


}
