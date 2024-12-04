package com.revup.achievement.listener;

import com.revup.achieve.dto.AchievementEventInfo;
import com.revup.achieve.listener.AchievementListener;
import com.revup.achievement.entity.UserAchievement;
import com.revup.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAchievementListener implements AchievementListener {

    private final AchievementService achievementService;

    @Async
    @Override
    @EventListener
    public void execute(AchievementEventInfo info) {
        List<UserAchievement> userAchievements = achievementService.checkAndUpdateAchievement(info);

        if(!userAchievements.isEmpty()) {
            //알림 전송
        }

        log.info("userAchievements = {}", userAchievements); //로그 확인용
    }
}
