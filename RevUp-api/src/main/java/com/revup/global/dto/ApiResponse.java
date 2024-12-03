package com.revup.global.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private String message;
    private T result;

    public static ApiResponse<Void> error(String message) {
        return new ApiResponse<>(message, null);
    }

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>("Success", result);
    }
}
