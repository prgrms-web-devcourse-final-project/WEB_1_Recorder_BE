package com.revup.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@Getter
@RequiredArgsConstructor
public enum SecurityUrlEndpoint {
    // Swagger
    SWAGGER_UI(HttpMethod.GET, "/swagger-ui/**"),
    SWAGGER_DOCS(HttpMethod.GET, "/v3/api-docs/**"),
    SWAGGER_HTML(HttpMethod.GET, "/swagger-ui.html"),
    SWAGGER_WEBJARS(HttpMethod.GET, "/webjars/**"),


    // All
    OAUTH_LOGIN(HttpMethod.GET, "/oauth2/authorization/**"),
    REFRESH_TOKEN(HttpMethod.POST, "/api/v1/auth/refresh"),


    //USER


    //FEEDBACK
    FEEDBACK_WEBSOCKET_CONNECT(HttpMethod.GET, "/ws/feedback/**"),
    FEEDBACK_SESSIONS(HttpMethod.GET, "/api/v1/feedback-code/sessions/*"),
    FEEDBACK_CODE_GET(HttpMethod.GET, "/api/v1/feedback-code/*"),
    FEEDBACK_CODE_UPDATE(HttpMethod.PATCH, "/api/v1/feedback-code/auto/*"),
    FEEDBACK_CODE_CREATE(HttpMethod.POST, "/api/v1/feedback-code"),
    MENTOR_LIST(HttpMethod.GET, "/api/v1/mentor**")
    ;

    private HttpMethod method;
    private String url;

    SecurityUrlEndpoint(HttpMethod method, String url) {
        this.method = method;
        this.url = url;
    }
}
