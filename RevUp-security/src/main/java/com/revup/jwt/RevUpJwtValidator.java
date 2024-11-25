package com.revup.jwt;

import com.revup.auth.dto.token.TokenInfo;
import com.revup.auth.service.TokenValidator;
import com.revup.constants.SecurityConstants;
import com.revup.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@RequiredArgsConstructor
public class RevUpJwtValidator implements TokenValidator {

    private final RevUpJwtProvider jwtProvider;

    @Override
    public void validateSameToken(String clientToken, String redisToken) {
        String originalToken = clientToken.substring(SecurityConstants.BEARER.length());
        validStringValue(originalToken, redisToken);
        validateTokenInfo(redisToken, originalToken);
    }

    private void validateTokenInfo(String redisToken, String originalToken) {
        TokenInfo redisUserInfo = jwtProvider.getTokenUserPrincipal(redisToken);
        TokenInfo clientUserInfo = jwtProvider.getTokenUserPrincipal(originalToken);
        if(!Objects.equals(clientUserInfo, redisUserInfo)) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    private static void validStringValue(String clientToken, String redisToken) {
        if(!redisToken.equals(clientToken)) {
            throw InvalidTokenException.EXCEPTION;
        }
    }
}
