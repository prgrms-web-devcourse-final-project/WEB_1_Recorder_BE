package com.revup.question.adaptor;

import com.revup.question.entity.Question;
import com.revup.question.exception.QuestionNotFoundException;
import com.revup.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionAdaptor {
    private final QuestionRepository questionRepository;

    public Question findById(Long id){
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
    }
}
