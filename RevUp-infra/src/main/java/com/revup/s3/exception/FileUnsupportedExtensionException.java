package com.revup.s3.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class FileUnsupportedExtensionException extends AppException {
    public FileUnsupportedExtensionException() {
        super(ErrorCode.FILE_UNSUPPORTED_EXTENSION);
    }
}
