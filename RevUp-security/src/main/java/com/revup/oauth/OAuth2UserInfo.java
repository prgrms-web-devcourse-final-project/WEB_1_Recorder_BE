package com.revup.oauth;

import com.revup.user.entity.LoginType;
import com.revup.user.entity.User;

import java.util.Map;

public interface OAuth2UserInfo {

    LoginType getProvider();

    Map<String, Object> getAttributes();

    String getId();

    String getEmail();

    String getName();

    String getFirstName();

    String getLastName();

    String getNickname();

    String getProfileImageUrl();

    User toEntity();
}
