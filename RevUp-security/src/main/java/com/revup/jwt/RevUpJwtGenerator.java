package com.revup.jwt;

import com.revup.auth.dto.token.AccessToken;
import com.revup.auth.dto.token.RefreshToken;
import com.revup.auth.dto.token.TokenInfo;
import com.revup.auth.dto.token.Tokens;
import com.revup.auth.service.TokenGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class RevUpJwtGenerator implements TokenGenerator {

    private static long ACCESS_EXPIRATION_TIME;
    private static long REFRESH_EXPIRATION_TIME;
    private static String SECRET_KEY;
    public static final String LOGIN_TYPE = "loginType";
    public static final String SOCIAL_ID = "socialId";

    public RevUpJwtGenerator(
            @Value("${jwt.refresh-expiration-time}") long refreshExpirationTime,
            @Value("${jwt.access-expiration-time}") long accessExpirationTime,
            @Value("${jwt.secret-key}") String secretKey
    ) {
        ACCESS_EXPIRATION_TIME = accessExpirationTime;
        REFRESH_EXPIRATION_TIME = refreshExpirationTime;
        SECRET_KEY = secretKey;
    }

    private static Key getKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    // Jwt 생성
    public Tokens generate(TokenInfo principal) {
        AccessToken accessToken = new AccessToken(
                generatorAccessToken(principal)
        );

        RefreshToken refreshToken = new RefreshToken(
                generatorRefreshToken(principal)
        );

        return Tokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private static String generatorAccessToken(TokenInfo principal) {
        return Jwts.builder()
                .setHeader(setHeader("ACCESS"))
                .setClaims(setClaims(principal))
                .setSubject(principal.socialId())
                .setIssuedAt(getNowDate())
                .setExpiration(new Date(getNowDate().getTime() + ACCESS_EXPIRATION_TIME))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private static String generatorRefreshToken(TokenInfo principal) {
        return Jwts.builder()
                .setHeader(setHeader("REFRESH"))
                .setClaims(setClaims(principal))
                .setSubject(principal.socialId())
                .setIssuedAt(getNowDate())
                .setExpiration(new Date(getNowDate().getTime() + REFRESH_EXPIRATION_TIME))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Map<String, Object> setHeader(String type) {
        Map<String, Object> header = new HashMap<>();
        header.put("type", "JWT");
        header.put("tokenType", type);
        header.put("alg", "HS256");
        return header;
    }

    private static Map<String, Object> setClaims(TokenInfo principal) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(LOGIN_TYPE, principal.loginType().getType());
        return claims;
    }

    private static Date getNowDate() {
        return new Date();
    }
}
