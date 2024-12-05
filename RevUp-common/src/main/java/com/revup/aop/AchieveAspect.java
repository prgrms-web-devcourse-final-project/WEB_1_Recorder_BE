package com.revup.aop;

import com.revup.achieve.AchieveType;
import com.revup.achieve.dto.AchievementEventInfo;
import com.revup.annotation.Achievable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
//업적과 관련된 메서드에서, @Achievable 선언 후
//userAchieveId(업적 체크할 사용자 pk)가 반드시 추가되어야 함
public class AchieveAspect {

    private final ApplicationEventPublisher eventPublisher;

    @AfterReturning(
            value = "@annotation(achievable)",
            returning = "result"
    )
    public void checkAchievements(
            JoinPoint joinPoint,
            Achievable achievable,
            Object result
    ) {
        String userField = achievable.userField();
        AchieveType[] types = achievable.type();
        Long targetUserId = extractUserId(result, userField);
        for (AchieveType type : types) {
            eventPublisher.publishEvent(
                    new AchievementEventInfo(targetUserId, type)
            );
        }
    }

    private Long extractUserId(Object result, String fieldName) {
        try {
            Class<?> clazz = result.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true); // private 필드 접근 허용
            return (Long) field.get(result);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("Error extracting field '{}': {}", fieldName, e.getMessage());
            return 0L;
        }
    }
}
