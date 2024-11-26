package com.revup.oauth.kakao;

import com.revup.oauth.OAuth2UserInfo;
import com.revup.user.entity.LoginType;
import com.revup.user.entity.User;
import lombok.ToString;

import java.util.Map;

@ToString
public class KakaoOauth2UserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;
    private final String id;
    private final String email;
    private final String name;
    private final String firstName;
    private final String lastName;
    private final String nickName;
    private final String profileImageUrl;

    public KakaoOauth2UserInfo(Map<String, Object> attributes) {
        // attributes 맵의 kakao_account 키의 값에 실제 attributes 맵이 할당되어 있음
        this.attributes = attributes;
        this.id = (String) attributes.get("id");
        this.email = (String) attributes.get("email");
        this.name = null;
        this.firstName = null;
        this.lastName = null;
        this.nickName = (String) attributes.get("nickname");
        this.profileImageUrl = (String) attributes.get("profile_image_url");
    }

    @Override
    public LoginType getProvider() {
        return LoginType.KAKAO;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getNickname() {
        return nickName;
    }

    @Override
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    @Override
    public User toEntity() {
        return User.builder()
                .socialEmail(email)
                .loginType(LoginType.KAKAO)
                .socialId(id)
                .build();
    }
}
