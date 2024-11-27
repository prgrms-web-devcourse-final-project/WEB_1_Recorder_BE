package com.revup.image.usecase;

import com.revup.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteImageUseCase {
    private final S3Service s3Service;

    public void execute(String filename){
        s3Service.deleteFileFromS3(filename);
    }

}
