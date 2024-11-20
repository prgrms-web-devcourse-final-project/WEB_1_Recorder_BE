package com.revup.question.service;

import com.revup.question.dto.QuestionBriefResponse;
import com.revup.question.dto.QuestionCreateInfo;
import com.revup.question.dto.QuestionIdResponse;
import com.revup.question.dto.QuestionPageInfo;
import com.revup.question.entity.Category;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionCategory;
import com.revup.question.entity.QuestionImage;
import com.revup.question.repository.CategoryRepository;
import com.revup.question.repository.QuestionCategoryRepository;
import com.revup.question.repository.QuestionImageRepository;
import com.revup.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;
    private final QuestionCategoryRepository questionCategoryRepository;
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
            Category category = categoryRepository.findByName(categoryName)
                    .orElseGet(() -> categoryRepository.save(Category.builder()
                            .name(categoryName)
                            .build()
                    ));
            QuestionCategory questionCategory = QuestionCategory
                    .builder()
                    .question(question)
                    .category(category)
                    .build();
            questionCategoryRepository.save(questionCategory);
        }

        for(String imageUrl:info.imageUrls()){
            QuestionImage questionImage = QuestionImage.builder()
                    .imageUrl(imageUrl)
                    .question(question)
                    .build();

            questionImageRepository.save(questionImage);
        }
        return new QuestionIdResponse(question.getId());

    }


    public List<QuestionBriefResponse> getQuestionList(QuestionPageInfo info) {
        List<Question> questions = questionRepository.findQuestionList(info.page(), info.size(),info.type());
        return questions.stream()
                .map(QuestionBriefResponse::of)
                .toList();
    }
}
