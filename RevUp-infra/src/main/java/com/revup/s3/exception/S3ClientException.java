package com.revup.s3.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class S3ClientException extends AppException {
    public S3ClientException(String message) {
        super(ErrorCode.S3_CLIENT,message);
    }
}
