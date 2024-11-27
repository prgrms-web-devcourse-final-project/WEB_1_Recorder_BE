package com.revup.jwt;

import com.revup.auth.dto.token.TokenInfo;
import com.revup.auth.dto.token.Tokens;
import com.revup.exception.ExpiredTokenException;
import com.revup.user.entity.LoginType;
import com.revup.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class RevUpJwtProviderTest {

    @InjectMocks private static RevUpJwtProvider jwtProvider;

    private static final String TEST_SECRET_KEY = "ae49c94fdee6f40ab8788e5b6d5656cef07699bd842011e558bcd2065add4d9d";

    @BeforeEach()
    void setUp() {
        jwtProvider = new RevUpJwtProvider(TEST_SECRET_KEY);
    }

    // 정상적인 토큰 생성
    private static RevUpJwtGenerator jwtGenerator() {
        return new RevUpJwtGenerator(
                1600000000000000L,
                1600000000000000L,
                TEST_SECRET_KEY
        );
    }

    // 만료된 토큰 생성
    private static RevUpJwtGenerator expiredJwtGenerator() {
        return new RevUpJwtGenerator(
                -10L,
                -10L ,
                TEST_SECRET_KEY
        );
    }

    private static User testUser() {
        return User.builder()
                .socialEmail("test@123@gmail.com")
                .loginType(LoginType.GITHUB)
                .socialId("1234")
                .build();
    }

    @Test
    @DisplayName("정상적인 토큰 생성")
    void generateToken() {
        User user = testUser();
        TokenInfo tokenInfo = new TokenInfo(1L, user.getSocialId(), user.getLoginType());
        Tokens generated = jwtGenerator().generate(tokenInfo);

        TokenInfo tokenUserPrincipal = jwtProvider.getTokenUserPrincipal(generated.accessToken().value());

        assertThat(tokenUserPrincipal.loginType()).isEqualTo(tokenInfo.loginType());
        assertThat(tokenUserPrincipal.socialId()).isEqualTo(tokenInfo.socialId());
    }

    @Test
    @DisplayName("유효기간이 만료된 토큰에서 사용자 조회시 예외가 발생한다.")
    void generateExpiredToken() {
        User user = testUser();
        TokenInfo tokenInfo = new TokenInfo(1L, user.getSocialId(), user.getLoginType());
        Tokens generated = expiredJwtGenerator().generate(tokenInfo);

        assertThatThrownBy(
                () -> jwtProvider.getTokenUserPrincipal(generated.accessToken().value()))
                .isInstanceOf(ExpiredTokenException.class)
                .hasMessage(ExpiredTokenException.EXCEPTION.getFormattedMessage());
    }
}