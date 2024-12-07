package com.revup.skillstack.usecase;

import com.revup.common.SkillStack;
import com.revup.question.service.QuestionService;
import com.revup.skillstack.model.mapper.SkillStackMapper;
import com.revup.skillstack.model.response.SkillStackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SkillStackUseCase {
    private final QuestionService questionService;
    private final SkillStackMapper skillStackMapper;

    public SkillStackResponse getAllStacks(){
        return skillStackMapper.toSkillStackResponse();
    }

    public SkillStackResponse getPopulars(int size){
        List<SkillStack> popularStacks = questionService.getPopularStacks(size);

        // 질문이 없어서 popularStacks가 비어있으면 기본 값 반환
        if (popularStacks.isEmpty()) {
            popularStacks = getDefaultSkillStacks(size);
        }

        return skillStackMapper.fromSkillStack(popularStacks);
    }

    private List<SkillStack> getDefaultSkillStacks(int size) {
        return Arrays.stream(SkillStack.values())
                .limit(size)
                .toList();
    }
}
