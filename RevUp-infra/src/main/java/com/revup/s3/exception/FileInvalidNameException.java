package com.revup.s3.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class FileInvalidNameException extends AppException {
    public FileInvalidNameException() {
        super(ErrorCode.FILE_INVALID_NAME);
    }
}
