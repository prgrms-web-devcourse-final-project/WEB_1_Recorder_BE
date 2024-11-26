package com.revup.question.dto.request;

import jakarta.validation.constraints.Min;

public record QuestionPageRequest(

        String type,

        @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
        int page,

        String state,

        String stack,

        String keyword
) {
}
