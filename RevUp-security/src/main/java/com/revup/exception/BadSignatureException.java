package com.revup.exception;

import com.revup.error.SecurityException;

import static com.revup.error.ErrorCode.TOKEN_BAD_SIGNATURE;

public class BadSignatureException extends SecurityException {

    public static final SecurityException EXCEPTION = new BadSignatureException();
    private BadSignatureException() {
        super(TOKEN_BAD_SIGNATURE);
    }
}
