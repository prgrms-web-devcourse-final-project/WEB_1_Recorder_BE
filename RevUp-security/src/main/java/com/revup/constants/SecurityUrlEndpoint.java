package com.revup.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@Getter
@RequiredArgsConstructor
public enum SecurityUrlEndpoint {

    // All
    OAUTH_LOGIN(HttpMethod.GET, "/oauth2/authorization/**"),
    REFRESH_TOKEN(HttpMethod.POST, "/api/v1/auth/refresh"),


    //USER


    //FEEDBACK
    FEEDBACK_CREATE(HttpMethod.POST, "/api/v1/feedback"),
    FEEDBACK_SESSIONS(HttpMethod.GET, "/api/v1/feedback-code/sessions/*"),
    FEEDBACK_CODE_GET(HttpMethod.GET, "/api/v1/feedback-code/*"),
    FEEDBACK_CODE_UPDATE(HttpMethod.PATCH, "/api/v1/feedback-code/auto/*"),
    FEEDBACK_CODE_CREATE(HttpMethod.POST, "/api/v1/feedback-code")
    ;

    private HttpMethod method;
    private String url;

    SecurityUrlEndpoint(HttpMethod method, String url) {
        this.method = method;
        this.url = url;
    }
}
