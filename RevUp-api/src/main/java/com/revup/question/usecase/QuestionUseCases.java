package com.revup.question.usecase;

import com.revup.page.Page;
import com.revup.question.dto.QuestionUpdateRequest;
import com.revup.question.dto.request.QuestionCreateRequest;
import com.revup.question.dto.request.QuestionPageRequest;
import com.revup.question.dto.response.QuestionBriefResponse;
import com.revup.question.dto.response.QuestionDetailsResponse;
import com.revup.question.dto.response.QuestionIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionUseCases {
    private final CreateQuestionUseCase createQuestionUseCase;
    private final GetQuestionListUseCase getQuestionListUseCase;
    private final GetQuestionDetailsUseCase getQuestionDetailsUseCase;
    private final UpdateQuestionUseCase updateQuestionUseCase;
    private final DeleteQuestionUseCase deleteQuestionUseCase;

    public QuestionIdResponse create(QuestionCreateRequest request) {
        return createQuestionUseCase.execute(request);
    }

    public Page<QuestionBriefResponse> getList(QuestionPageRequest request) {
        return getQuestionListUseCase.execute(request);
    }

    public QuestionDetailsResponse getDetails(Long id) {
        return getQuestionDetailsUseCase.execute(id);
    }

    public QuestionIdResponse update(QuestionUpdateRequest request) {
        return updateQuestionUseCase.execute(request);
    }

//    public void delete(Long id) {
//        deleteQuestionUseCase.execute(id);
//    }
}
