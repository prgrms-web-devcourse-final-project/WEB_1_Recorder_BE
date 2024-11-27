package com.revup.oauth.google;

import com.revup.oauth.OAuth2UserInfo;
import com.revup.user.entity.LoginType;
import com.revup.user.entity.User;
import lombok.ToString;

import java.util.Map;


@ToString
public class GoogleOauth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;
    private final String id;
    private final String email;
    private final String name;
    private final String firstName;
    private final String lastName;
    private final String nickName;
    private final String profileImageUrl;

    public GoogleOauth2UserInfo(Map<String, Object> attributes) {

        this.attributes = attributes;
        this.id = (String)attributes.get("id");
        this.name = (String) attributes.get("name");
        this.firstName = String.valueOf(attributes.get("given_name"));
        this.lastName = String.valueOf(attributes.get("family_name"));
        this.email = String.valueOf(attributes.get("email"));
        this.nickName = null;
        this.profileImageUrl = (String) attributes.get("picture");
    }

    @Override
    public LoginType getProvider() {
        return LoginType.GOOGLE;
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
                .loginType(LoginType.GOOGLE)
                .socialId(id)
                .build();
    }

}
