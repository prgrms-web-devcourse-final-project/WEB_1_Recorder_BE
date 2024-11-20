package com.revup.question.service;

import com.revup.image.entity.QuestionImage;
import com.revup.image.repository.QuestionImageRepository;
import com.revup.question.dto.QuestionBriefResponse;
import com.revup.question.dto.QuestionCreateInfo;
import com.revup.question.dto.QuestionIdResponse;
import com.revup.question.dto.QuestionPageInfo;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionTag;
import com.revup.question.entity.Tag;
import com.revup.question.repository.QuestionRepository;
import com.revup.question.repository.QuestionTagRepository;
import com.revup.question.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TagRepository tagRepository;
    private final QuestionTagRepository questionTagRepository;
    private final QuestionImageRepository questionImageRepository;

    @Transactional
    public QuestionIdResponse create(QuestionCreateInfo info) {
        Question question = Question.builder()
                .title(info.title())
                .content(info.content())
                .type(info.type())
                .state(info.state())
                .isAnonymous(info.isAnonymous())
                .build();
        questionRepository.save(question);

        for (String categoryName : info.categories()) {
            Tag tag = tagRepository.findByName(categoryName)
                    .orElseGet(() -> tagRepository.save(Tag.builder()
                            .name(categoryName)
                            .build()
                    ));
            QuestionTag questionTag = QuestionTag
                    .builder()
                    .question(question)
                    .tag(tag)
                    .build();
            questionTagRepository.save(questionTag);
        }

        for (String imageUrl : info.imageUrls()) {
            QuestionImage questionImage = QuestionImage.builder()
                    .imageUrl(imageUrl)
                    .question(question)
                    .build();

            questionImageRepository.save(questionImage);
        }
        return new QuestionIdResponse(question.getId());

    }


    public List<QuestionBriefResponse> getQuestionList(QuestionPageInfo info) {
        List<Question> questions = questionRepository.findQuestionList(info.page(), info.size(), info.type());
        return questions.stream()
                .map(QuestionBriefResponse::of)
                .toList();
    }

}
