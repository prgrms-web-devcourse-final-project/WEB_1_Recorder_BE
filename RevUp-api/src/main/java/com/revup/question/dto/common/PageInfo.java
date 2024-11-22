package com.revup.question.dto.common;

public record PageInfo(
        int startPage,
        int endPage,
        boolean prev,
        boolean next
) {
}
