package com.revup.question.controller;

import com.revup.global.dto.ApiResponse;
import com.revup.s3.dto.ImageUrlResponse;
import com.revup.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.revup.global.dto.ApiResponse.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileController {
    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<ApiResponse<ImageUrlResponse>> uploadFile(@RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(success(s3Service.uploadFileToS3(file)));
    }
}
