package com.revup.achievement.adaptor;


import com.revup.achieve.AchieveType;
import com.revup.annotation.Adaptor;
import com.revup.answer.repository.AnswerRepository;
import com.revup.achievement.entity.Achievement;
import com.revup.achievement.entity.UserAchievement;
import com.revup.achievement.repository.AchievementRepository;
import com.revup.achievement.repository.UserAchievementRepository;
import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.feedback.repository.FeedbackRepository;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Adaptor
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AchievementAdaptor {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AnswerRepository answerRepository;
    private final FeedbackRepository feedbackRepository;

    public List<Achievement> findByType(AchieveType type) {
        return achievementRepository.findByType(type);
    }

    public List<UserAchievement> findAchievementLog(User user, AchieveType type) {
        return userAchievementRepository.findAllByUserAndAchievement_Type(user, type);
    }

    @Transactional
    public List<UserAchievement> saveUserAchievements(List<UserAchievement> newAchievements) {
        Iterable<UserAchievement> results = userAchievementRepository.saveAll(newAchievements);

        ArrayList<UserAchievement> userAchievements = new ArrayList<>();
        for (UserAchievement result : results) {
            userAchievements.add(result);
        }

        return userAchievements;
    }

    public List<LocalDate> findCreateDatesByUser(Long userId) {
        return answerRepository.findCreatedAtsByUserId(userId);
    }

    public List<Integer> findGoodCountsByUser(Long userId) {
        return answerRepository.findGoodCountsByUserId(userId);
    }

    public List<Long> findFeedbackByTeacher(Long teacherId) {
        return feedbackRepository.findRequestedFeedBackIdsByUserId(teacherId);
    }

    public List<Long> findAllIds() {
        return achievementRepository.findAllIds();
    }

    public List<Achievement> findAll() {
        List<Achievement> list = achievementRepository.findAllAchievements();
        log.info("list = {}", list);
        return list;
    }

    public UserAchievement findById(Long userAchievementId) {
        return userAchievementRepository.findById(userAchievementId)
                .orElseThrow(() -> new AppException(ErrorCode.ACHIEVEMENT_STACK_NOT_FOUND));
    }
}
