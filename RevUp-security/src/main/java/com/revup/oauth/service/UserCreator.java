package com.revup.oauth.service;

import com.revup.error.AppException;
import com.revup.oauth.OAuth2UserPrincipal;
import com.revup.oauth.google.GoogleOauth2UserInfo;
import com.revup.oauth.kakao.KakaoOauth2UserInfo;
import com.revup.user.entity.User;
import com.revup.user.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.revup.error.ErrorCode.LOGIN_ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreator {

    private static final String KAKAO_ISSUER = "https://kauth.kakao.com";
    private static final String GOOGLE_ISSUER = "https://accounts.google.com";
    private final OAuthService oAuthService;

    public User create(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        log.info("principal = {}", principal);

        User loginUserInfo = handlePrincipal(principal);
        log.info("create().User Entity = {}", loginUserInfo);
        return oAuthService.loginOrSignup(loginUserInfo);
    }

    private User handlePrincipal(Object principal) {
        if (principal instanceof OidcUser oidcUser) {
            String iss = oidcUser.getAttribute("iss").toString();
            log.info("iss = {}", iss);

            if (KAKAO_ISSUER.equals(iss)) {
                return handleKakaoUser(oidcUser);  // 카카오 처리
            } else if (GOOGLE_ISSUER.equals(iss)) {
                return handleGoogleUser(oidcUser);  // 구글 처리
            }

        } else if (principal instanceof OAuth2UserPrincipal oAuth2User) {
            return oAuth2User.userInfo().toEntity();
        }

        throw new AppException(LOGIN_ERROR);
    }

    private User handleGoogleUser(OidcUser oidcUser) {
        Map<String, Object> oidcAttributes = oidcUser.getAttributes();

        Map<String, Object> attributes = Map.of(
                "id", oidcUser.getName(),
                "email", oidcAttributes.get("email"),
                "name", oidcAttributes.get("name") == null ? "익명" : oidcAttributes.get("name") ,
                "given_name", oidcAttributes.get("given_name") == null ? "" : oidcAttributes.get("given_name"),
                "family_name", oidcAttributes.get("family_name")== null ? "" : oidcAttributes.get("family_name"),
                "picture", oidcAttributes.get("picture") == null ? "" : oidcAttributes.get("picture")
        );

        //accessToken 생성(type, oidcUser.getName())으로 생성해야지
        return new OAuth2UserPrincipal(new GoogleOauth2UserInfo(attributes))
                .userInfo()
                .toEntity();
    }

    private User handleKakaoUser(OidcUser oidcUser) {
        log.info("oidcUser = {}", oidcUser);

        // Kakao User 정보를 가져와서 처리
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("id", oidcUser.getName());
        attributes.put("email", oidcUser.getEmail());
        attributes.put("name", oidcUser.getAttribute("name"));
        attributes.put("profile_image", oidcUser.getProfile());


        return new OAuth2UserPrincipal(new KakaoOauth2UserInfo(attributes))
                .userInfo()
                .toEntity();
    }
}
