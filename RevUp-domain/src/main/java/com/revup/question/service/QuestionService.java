package com.revup.question.service;

import com.revup.image.entity.QuestionImage;
import com.revup.image.repository.QuestionImageRepository;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionTag;
import com.revup.question.entity.QuestionType;
import com.revup.question.repository.QuestionRepository;
import com.revup.question.repository.QuestionTagRepository;
import com.revup.question.repository.TagRepository;
import com.revup.tag.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionTagRepository questionTagRepository;
    private final QuestionImageRepository questionImageRepository;
    private final TagRepository tagRepository;

    @Transactional
    public Long createQuestion(Question question, List<String> tagNames, List<QuestionImage> images) {

        questionRepository.save(question);

        for (String name : tagNames) {
            Tag tag = tagRepository.findByName(name)
                    .orElseGet(() -> {
                        Tag newTag = Tag.builder()
                                .name(name)
                                .build();
                        return tagRepository.save(newTag);
                    });

            QuestionTag questionTag = QuestionTag
                    .builder()
                    .question(question)
                    .tag(tag)
                    .build();
            questionTagRepository.save(questionTag);
        }

        questionImageRepository.saveAll(images);

        return question.getId();

    }

    public List<Question> getQuestionsByPage(QuestionType type, long offset, int size) {
        return questionRepository.findQuestionsByType(type, offset, size);
    }

    public long getTotalQuestionCount(QuestionType type) {
        return questionRepository.countQuestionsByType(type);
    }
}
