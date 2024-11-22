package com.revup.config.converter;

import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;

public class RevUpAuthorizationCodeGrantRequestEntityConverter extends OAuth2AuthorizationCodeGrantRequestEntityConverter {

    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        RequestEntity<?> entity = super.convert(authorizationCodeGrantRequest);

        // POST 방식으로 클라이언트 인증 정보를 추가합니다.
        return RequestEntity
                .post(entity.getUrl())
                .headers(entity.getHeaders())
                .body(entity.getBody());
    }
}

