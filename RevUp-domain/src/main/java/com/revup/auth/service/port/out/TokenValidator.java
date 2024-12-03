package com.revup.auth.service.port.out;


public interface TokenValidator {

    void validateSameToken(String clientToken, String serverToken);
}
