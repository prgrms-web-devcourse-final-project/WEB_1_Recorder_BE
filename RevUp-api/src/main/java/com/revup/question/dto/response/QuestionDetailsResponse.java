package com.revup.question.dto.response;

import com.revup.answer.dto.response.AnswerDetailsResponse;
import com.revup.common.BooleanStatus;
import com.revup.common.SkillStack;
import com.revup.question.entity.Question;

import java.util.List;

public record QuestionDetailsResponse(
        Long id,
        String writer,
        String profileImage,
        String title,
        String content,
        String createdAt,
        int answerCount,
        int readCount,
        String githubLink,
        List<String> stacks,
        List<QuestionCodeResponse> codes
) {
    public static QuestionDetailsResponse of(Question question) {
        return new QuestionDetailsResponse(
                question.getId(),
                question.getIsAnonymous().equals(BooleanStatus.FALSE) ? question.getUser().getNickname() : "익명",
                question.getUser().getProfile().getProfileImage(),
                question.getTitle(),
                question.getContent(),
                question.getCreatedAt().toString(),
                question.getAnswerCount(),
                question.getReadCount(),
                question.getGithubLinkReveal().toBoolean() ? question.getGithubLink() : "링크 비공개",
                question.getStacks()
                        .stream()
                        .map(SkillStack::getContent)
                        .toList(),
                question.getCodes()
                        .stream()
                        .map(QuestionCodeResponse::of)
                        .toList()
        );
    }

}
