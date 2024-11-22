package com.revup.jwt;

import com.revup.auth.dto.token.AccessToken;
import com.revup.auth.dto.token.RefreshToken;
import com.revup.auth.service.TokenValidator;
import com.revup.error.SecurityException;
import com.revup.exception.ExpiredTokenException;
import com.revup.exception.UnsupportedTokenException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

import static com.revup.error.ErrorCode.UNKNOWN_EXCEPTION;

@Component
public class RevUpJwtValidator implements TokenValidator {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    private Key getKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }
    @Override
    public void validate(AccessToken token) {
        validateToken(token.value());
    }

    @Override
    public void validate(RefreshToken token) {
        validateToken(token.value());
    }
    private void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            throw UnsupportedTokenException.EXCEPTION;
        } catch (Exception e) {
            throw new SecurityException(UNKNOWN_EXCEPTION);
        }
    }
}
