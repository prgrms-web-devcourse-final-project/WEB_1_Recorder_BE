package com.revup.achievement.service;

import com.revup.achievement.adaptor.AchievementAdaptor;
import com.revup.answer.dto.AnswerDto;
import com.revup.achievement.entity.Achievement;
import com.revup.achievement.entity.UserAchievement;
import com.revup.common.BooleanStatus;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementChecker {

    private final AchievementAdaptor achievementAdaptor;

    //채택 답변수
    public List<UserAchievement> handleAdoption(User user, List<Achievement> achievements) {
        List<AnswerDto> answers = achievementAdaptor.findAllAnswerByUser(user.getId());
        long acceptionCount = answers.stream()
                .filter(answer -> answer.isAccept().equals(BooleanStatus.TRUE))
                .count();

        return getNewUserAchievements(user, achievements, acceptionCount);
    }

    //누적 좋아요
    public List<UserAchievement> handleLikes(User user, List<Achievement> achievements) {
        List<Integer> goodCounts = achievementAdaptor.findGoodCountsByUser(user.getId());

        long totalGoodCount = goodCounts.stream()
                .mapToLong(count -> count)
                .sum();

        return getNewUserAchievements(user, achievements, totalGoodCount);
    }

    //받은 피드백 요청 수
    public List<UserAchievement> handleFeedback(User user, List<Achievement> achievements) {
        List<Long> requestedFeedback = achievementAdaptor.findFeedbackByTeacher(user.getId());
        long total = requestedFeedback.size();

        return getNewUserAchievements(user, achievements, total);
    }

    //연속 답변일수
    public List<UserAchievement> handleAnswers(User user, List<Achievement> achievements) {
        List<LocalDate> createdDates = achievementAdaptor.findCreateDatesByUser(user.getId());
        if(createdDates.isEmpty()) return List.of();

        int maxDate = createdDates.stream()
                .reduce(0, (currentMax, date) -> {
                    if (currentMax == 0) {
                        return 1;
                    }
                    int prevIndex = createdDates.indexOf(date) - 1;
                    if (prevIndex >= 0 && createdDates.get(prevIndex).plusDays(1).equals(date)) {
                        return currentMax + 1;
                    }
                    return 1;
                }, Integer::max);

        return getNewUserAchievements(user, achievements, maxDate);

    }

    //채택률
    public List<UserAchievement> handleRate(User user, List<Achievement> achievements) {
        List<AnswerDto> answers = achievementAdaptor.findAllAnswerByUser(user.getId());

        //비율 계산
        long total = answers.size();
        long acceptionCount = answers.stream()
                .filter(answer -> answer.isAccept().equals(BooleanStatus.TRUE))
                .count();

        if(total == 0) return List.of();

        BigDecimal numerator = new BigDecimal(acceptionCount);
        BigDecimal denominator = new BigDecimal(total);

        //%로 변환
        long percentage = numerator
                .divide(denominator, 4, RoundingMode.HALF_UP) // 소수점 4자리까지, 반올림
                .multiply(BigDecimal.valueOf(100)).longValue();

        return achievements.stream()
                .filter(achievement -> achievement.getStandard() <= percentage)
                .map(achievement -> UserAchievement.of(user, achievement))
                .toList();
    }

    private static List<UserAchievement> getNewUserAchievements(User user, List<Achievement> achievements, long value) {
        return achievements.stream()
                .filter(achievement -> achievement.getStandard() <= value)
                .map(achievement -> UserAchievement.of(user, achievement))
                .toList();
    }
}
