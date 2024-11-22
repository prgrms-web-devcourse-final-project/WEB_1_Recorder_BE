package com.revup.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class BlockedContentValidator implements ConstraintValidator<BlockedContent, String> {
    private static final List<String> BLOCKED_WORDS = List.of(
            "야동", "포르노", "AV", "광고",
            "19금", "도박", "바카라", "카지노",
            "섹스", "업소", "불법", "토렌트",
            "스팸", "음란", "성매매"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return BLOCKED_WORDS.stream().noneMatch(value::contains);
    }
}
