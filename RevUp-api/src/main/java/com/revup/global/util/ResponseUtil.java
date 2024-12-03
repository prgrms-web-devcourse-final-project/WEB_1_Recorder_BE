package com.revup.global.util;

import com.revup.global.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(HttpStatus status,T data) {
        return ResponseEntity.status(status).body(ApiResponse.success(data));
    }

    public static ResponseEntity<ApiResponse<Void>> failure(HttpStatus status,String message){
        return ResponseEntity.status(status).body(ApiResponse.error(message));
    }
}
