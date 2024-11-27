package com.revup.common;

import com.revup.common.exception.InvalidSkillStackException;

public enum SkillStack {
    JAVA,
    SPRING,
    REACT,
    PYTHON,
    JAVASCRIPT,
    TYPESCRIPT,
    NODEJS,
    VUEJS,
    KOTLIN,
    SWIFT,
    C,
    CPLUSPLUS,
    RUBY,
    PHP,
    SCALA,
    AWS,
    GCP,
    DOCKER,
    KUBERNETES,
    MONGODB,
    MYSQL,
    POSTGRESQL,
    FLUTTER,
    DART,
    UNITY,
    UNREAL;

    public static SkillStack of(String stack) {
        try {
            return SkillStack.valueOf(stack.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidSkillStackException();
        }
    }


}
