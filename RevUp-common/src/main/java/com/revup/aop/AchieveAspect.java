package com.revup.aop;

import com.revup.achieve.AchieveType;
import com.revup.achieve.dto.AchievementEventInfo;
import com.revup.annotation.Achievable;
import com.revup.annotation.SecurityUser;
import com.revup.user.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AchieveAspect {

    private final ApplicationEventPublisher eventPublisher;
    private final UserUtil userUtil;

    @AfterReturning(
            value = "@annotation(com.revup.annotation.Achievable)"
    )
    public void checkAchievements(JoinPoint joinPoint) {
        AchieveType type = extractType(joinPoint);
        log.info("type = {}", type);
        Long subject = userUtil.getSubject();
        log.info("subject = {}", subject);
        eventPublisher.publishEvent(
                new AchievementEventInfo(subject, type)
        );
    }

    private AchieveType extractType(JoinPoint joinPoint) {
        Method method = getMethodFromJoinPoint(joinPoint);
        Achievable achievable = method.getAnnotation(Achievable.class);
        return achievable.type();
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
