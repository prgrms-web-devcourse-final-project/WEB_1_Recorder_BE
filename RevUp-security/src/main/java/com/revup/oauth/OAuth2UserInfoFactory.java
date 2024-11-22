package com.revup.oauth;

import com.revup.oauth.exception.Oauth2AuthenticationProcessingException;
import com.revup.oauth.github.GithubOauth2UserInfo;
import com.revup.oauth.google.GoogleOauth2UserInfo;
import com.revup.oauth.kakao.KakaoOauth2UserInfo;
import com.revup.user.entity.LoginType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId,
                                                   Map<String, Object> attributes) {
        if (LoginType.GOOGLE.getType().equals(registrationId)) {
            return new GoogleOauth2UserInfo(attributes);
        } else if (LoginType.GITHUB.getType().equals(registrationId)) {
            return new GithubOauth2UserInfo(attributes);
        } else if (LoginType.KAKAO.getType().equals(registrationId)) {
            return new KakaoOauth2UserInfo(attributes);
        } else {
            throw new Oauth2AuthenticationProcessingException("Login with " + registrationId + " is not supported");
        }
    }
}
