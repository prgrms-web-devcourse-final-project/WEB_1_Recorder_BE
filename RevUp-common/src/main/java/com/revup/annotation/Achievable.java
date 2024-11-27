package com.revup.annotation;


import com.revup.achieve.AchieveType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 업적과 관련된 기능에 명시.
 * 해당 메서드가 종료 후, 종류에 따라서 실행할 집계합수를 다르게 할 예정
 * (일단 생각만 가지고 있음.)
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Achievable {

    AchieveType type();
}
