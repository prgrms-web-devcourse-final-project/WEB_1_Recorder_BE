package com.revup.common;

public enum SkillStack {

    REACT,
    JAVA,
    KOTLIN,
    SPRING,
    SPRINGBOOT
    ;

    public static SkillStack from(String value){
        return SkillStack.valueOf(value);
    }

}
