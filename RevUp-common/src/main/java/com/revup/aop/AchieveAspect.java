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
import java.lang.reflect.Method;

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
            return 1L;
        }
    }

    private Method getMethodFromJoinPoint(JoinPoint joinPoint) {
        // 호출된 메서드 정보 가져오기
        String methodName = joinPoint.getSignature().getName();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Object[] args = joinPoint.getArgs();

        try {
            // 메서드 매개변수를 활용해 정확한 메서드 가져오기
            for (Method method : targetClass.getDeclaredMethods()) {
                if (method.getName().equals(methodName) &&
                        method.getParameterCount() == args.length) {
                    return method;
                }
            }
        } catch (Exception e) {
            log.error("메서드 정보를 가져오는 중 오류 발생: {}", e.getMessage());
        }
        throw new IllegalStateException("메서드를 찾을 수 없습니다.");
    }
}
