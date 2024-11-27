package com.revup.user.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class UserPermissionException extends AppException {
    public UserPermissionException() {
        super(ErrorCode.USER_PERMISSION);
    }
}
