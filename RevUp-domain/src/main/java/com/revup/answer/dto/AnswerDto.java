package com.revup.answer.dto;

import com.revup.common.BooleanStatus;

public record AnswerDto(
        Long id,
        BooleanStatus isAccept
) {
}
