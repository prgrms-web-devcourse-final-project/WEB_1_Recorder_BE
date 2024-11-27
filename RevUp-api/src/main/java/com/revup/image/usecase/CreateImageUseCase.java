package com.revup.image.usecase;

import com.revup.image.dto.response.ImageResponse;
import com.revup.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CreateImageUseCase {
    private final S3Service s3Service;

    public ImageResponse execute(MultipartFile file) throws IOException {
        Map<String, String> result = s3Service.uploadFileToS3(file);
        return new ImageResponse(
                result.get("fileName"),
                result.get("url")
        );
    }
}
