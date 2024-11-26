package com.revup.oauth.github;

import com.revup.oauth.OAuth2UserInfo;
import com.revup.user.dto.Email;
import com.revup.user.entity.LoginType;
import com.revup.user.entity.User;
import lombok.ToString;

import java.util.Map;

@ToString
public class GithubOauth2UserInfo implements OAuth2UserInfo, EmailUpdatable {

    private final Map<String, Object> attributes;
    private final String id;
    private String email;
    private final String name;
    private final String firstName;
    private final String lastName;
    private final String nickName;
    private final String profileImageUrl;

    public GithubOauth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        // GitHub API에서 제공하는 속성 매핑
        this.id = String.valueOf(attributes.get("id")); // GitHub의 사용자 고유 ID는 숫자 형태
        this.name = (String) attributes.get("name"); // 전체 이름
        this.nickName = (String) attributes.get("login"); // GitHub 닉네임 (username)
        this.profileImageUrl = (String) attributes.get("avatar_url"); // 프로필 이미지 URL

        // GitHub는 firstName과 lastName을 제공하지 않음
        this.firstName = null;
        this.lastName = null;
    }

    @Override
    public LoginType getProvider() {
        return LoginType.GITHUB;
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
    public void updateEmail(Email email) {
        this.email = email.value();
    }

    @Override
    public User toEntity() {
        return User.builder()
                .socialEmail(email)
                .loginType(LoginType.GITHUB)
                .socialId(id)
                .build();
    }
}
