package com.revup.auth.service;


public interface TokenValidator {

    void validateSameToken(String clientToken, String serverToken);
}
