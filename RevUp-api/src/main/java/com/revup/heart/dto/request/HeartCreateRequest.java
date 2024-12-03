package com.revup.heart.dto.request;

public record HeartCreateRequest(
        Long answerId,
        String type
) {
}
