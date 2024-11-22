package com.revup.s3.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class S3ServiceException extends AppException {
    public S3ServiceException(String message) {
        super(ErrorCode.S3_SERVICE, message);
    }
}
