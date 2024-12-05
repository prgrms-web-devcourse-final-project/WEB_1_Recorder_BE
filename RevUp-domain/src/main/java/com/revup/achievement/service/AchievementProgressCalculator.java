package com.revup.achievement.service;

import com.revup.achievement.adaptor.AchievementAdaptor;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementProgressCalculator {

    private final AchievementAdaptor achievementAdaptor;

    public BigDecimal calculateAdoption(User user) {
        return BigDecimal.valueOf(user.getAdoptedAnswerCount());
    }

    public BigDecimal calculateLikes(User user) {
        List<Integer> goodCounts = achievementAdaptor.findGoodCountsByUser(user.getId());
        long totalLikes = goodCounts.stream()
                .mapToLong(count -> count)
                .sum();
        return BigDecimal.valueOf(totalLikes);
    }

    public BigDecimal calculateRequestedFeedbackCount(User user) {
        List<Long> requestedFeedback = achievementAdaptor.findFeedbackByTeacher(user.getId());
        return BigDecimal.valueOf(requestedFeedback.size());
    }

    public BigDecimal calculateAnswerDate(User user) {
        List<LocalDate> createdDates = achievementAdaptor.findCreateDatesByUser(user.getId());
        if(createdDates.isEmpty()) return BigDecimal.ZERO;
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
        return BigDecimal.valueOf(maxDate);
    }

    public BigDecimal calculateAdoptionRate(User user) {
        long adopted = user.getAdoptedAnswerCount();
        long total = user.getTotalAnswerCount();

        if(total == 0) return BigDecimal.ZERO;

        //비율 계산
        BigDecimal numerator = new BigDecimal(adopted);
        BigDecimal denominator = new BigDecimal(total);

        //%로 변환
        return numerator.divide(denominator, 4, RoundingMode.HALF_UP) // 소수점 4자리까지, 반올림
                .multiply(BigDecimal.valueOf(100));
    }
}
