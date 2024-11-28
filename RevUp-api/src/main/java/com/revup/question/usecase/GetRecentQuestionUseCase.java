package com.revup.question.usecase;

import com.revup.global.dto.ApiResponse;
import com.revup.question.dto.response.QuestionBriefResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetRecentQuestionUseCase {
    public ApiResponse<List<QuestionBriefResponse>> execute() {
        return null;
    }
}
