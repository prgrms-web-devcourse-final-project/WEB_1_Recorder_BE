package com.revup.oauth.exception;


import org.springframework.security.core.AuthenticationException;

public class Oauth2AuthenticationProcessingException extends AuthenticationException {

    public Oauth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}
