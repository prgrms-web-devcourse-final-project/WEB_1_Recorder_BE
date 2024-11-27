package com.revup.s3.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.revup.s3.exception.S3ClientException;
import com.revup.s3.exception.S3ServiceException;
import com.revup.s3.validation.FileValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public Map<String, String> uploadFileToS3(MultipartFile file) throws IOException {

        FileValidator.validateFile(file);

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata);
            amazonS3.putObject(putObjectRequest);

        } catch (AmazonServiceException e) {
            throw new S3ServiceException(e.getMessage());
        } catch (AmazonClientException e) {
            throw new S3ClientException(e.getMessage());
        }

        String url = amazonS3.getUrl(bucket, fileName).toString();
        Map<String, String> result = new HashMap<>();
        result.put("fileName", fileName);
        result.put("url", url);
        return result;
    }

    public void deleteFileFromS3(String fileName) {
        try {
            amazonS3.deleteObject(bucket, fileName);
        } catch (AmazonServiceException e) {
            throw new S3ServiceException(e.getMessage());
        } catch (AmazonClientException e) {
            throw new S3ClientException(e.getMessage());
        }
    }

}
