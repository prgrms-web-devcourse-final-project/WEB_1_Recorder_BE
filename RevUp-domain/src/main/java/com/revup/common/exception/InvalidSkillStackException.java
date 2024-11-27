package com.revup.common.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class InvalidSkillStackException extends AppException {
    public InvalidSkillStackException() {
        super(ErrorCode.SKILL_INVALID);
    }
}
