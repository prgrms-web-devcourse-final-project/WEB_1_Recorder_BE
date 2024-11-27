package com.revup.image.controller;

import com.revup.global.dto.ApiResponse;
import com.revup.image.dto.response.ImageResponse;
import com.revup.image.usecase.CreateImageUseCase;
import com.revup.image.usecase.DeleteImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.revup.global.dto.ApiResponse.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {
    private final CreateImageUseCase createImageUseCase;
    private final DeleteImageUseCase deleteImageUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<ImageResponse>> uploadFile(@RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(success(createImageUseCase.execute(file)));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFile(@RequestParam String fileName){
        deleteImageUseCase.execute(fileName);
        return ResponseEntity.noContent().build();
    }


}
