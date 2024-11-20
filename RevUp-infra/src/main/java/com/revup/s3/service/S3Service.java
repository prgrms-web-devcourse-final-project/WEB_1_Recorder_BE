package com.revup.s3.service;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.revup.s3.dto.ImageUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public ImageUrlResponse uploadFileToS3(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata);
            amazonS3.putObject(putObjectRequest);

        } catch (AmazonServiceException e) {
            // Amazon S3 서비스 측에서 발생한 오류 처리
            throw new IOException("Amazon S3 서비스 오류: " + e.getMessage(), e);
        } catch (AmazonClientException e) {
            // 클라이언트 측 오류 처리 (네트워크 문제 등)
            throw new IOException("클라이언트 오류: " + e.getMessage(), e);
        }

        return new ImageUrlResponse(amazonS3.getUrl(bucket, fileName).toString()); // S3에서 접근 가능한 URL 반환
    }

}
