package com.revup.exception;

import com.revup.error.SecurityException;

import static com.revup.error.ErrorCode.TOKEN_NOT_SUPPORTED;

public class UnsupportedTokenException extends SecurityException {

    public static final SecurityException EXCEPTION = new UnsupportedTokenException();
    private UnsupportedTokenException() {
        super(TOKEN_NOT_SUPPORTED);
    }
}
