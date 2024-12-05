package com.revup.user.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class UserIdNotFoundException extends AppException {
    public UserIdNotFoundException(Long id) {
        super(ErrorCode.USER_ID_NOT_FOUND,id);
    }
}
