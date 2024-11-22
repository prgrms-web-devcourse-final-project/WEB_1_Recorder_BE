package com.revup.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@Getter
@RequiredArgsConstructor
public enum SecurityUrlEndpoint {

    // All
    OAUTH_LOGIN(HttpMethod.GET, "/oauth2/authorization/**"),
    REFRESH_TOKEN(HttpMethod.POST, "/api/v1/auth/refresh")
    ;


    //USER

    private HttpMethod method;
    private String url;

    SecurityUrlEndpoint(HttpMethod method, String url) {
        this.method = method;
        this.url = url;
    }
}
