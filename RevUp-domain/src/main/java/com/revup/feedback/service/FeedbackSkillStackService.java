package com.revup.feedback.service;

import com.revup.feedback.entity.FeedbackSkillStack;
import com.revup.feedback.repository.FeedbackSkillStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackSkillStackService {

    private final FeedbackSkillStackRepository feedbackSkillStackRepository;

    public FeedbackSkillStack feedbackSkillStackCreate(FeedbackSkillStack feedbackSkillStack) {
        return feedbackSkillStackRepository.save(feedbackSkillStack);
    }

}
