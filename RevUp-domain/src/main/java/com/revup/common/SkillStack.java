package com.revup.common;

import com.revup.common.exception.InvalidSkillStackException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SkillStack {
    JAVA("Java"),
    SPRING("Spring"),
    SPRINGBOOT("Spring Boot"),
    REACT("React"),
    PYTHON("Python"),
    JAVASCRIPT("Javascript"),
    TYPESCRIPT("Typescript"),
    NODEJS("Node.js"),
    VUEJS("Vue.js"),
    KOTLIN("Kotlin"),
    SWIFT("Swift"),
    C("C"),
    CPLUSPLUS("C++"),
    RUBY("Ruby"),
    PHP(""),
    SCALA("Scala"),
    AWS("AWS"),
    GCP("GCP"),
    DOCKER("Docker"),
    KUBERNETES("Kubernates"),
    MONGODB("Mongo DB"),
    MYSQL("MySQL"),
    POSTGRESQL("Postgre SQL"),
    FLUTTER("Flutter"),
    DART("Dart"),
    UNITY("Unity"),
    UNREAL("Unreal");

    private final String content;
    public static SkillStack of(String stack) {
        try {
            return SkillStack.valueOf(stack.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidSkillStackException();
        }
    }

    public static SkillStack from(String stack) {
        for (SkillStack value : values()) {
            if(value.content.equals(stack)) {
                return value;
            }
        }

        throw new InvalidSkillStackException();
    }

}
