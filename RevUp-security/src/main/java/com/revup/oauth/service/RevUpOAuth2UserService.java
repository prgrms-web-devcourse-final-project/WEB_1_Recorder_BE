package com.revup.oauth.service;

import com.revup.oauth.OAuth2UserInfo;
import com.revup.oauth.OAuth2UserInfoFactory;
import com.revup.oauth.OAuth2UserPrincipal;
import com.revup.oauth.exception.Oauth2AuthenticationProcessingException;
import com.revup.oauth.github.EmailUpdatable;
import com.revup.user.dto.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RevUpOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        // GitHub에서 정보 추가 처리
        if ("github".equals(registrationId)) {
            return processGitHubUser(oAuth2UserRequest, oAuth2User);
        }

        return processOAuth2User(oAuth2UserRequest, oAuth2User);
    }

    private OAuth2User processGitHubUser(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        // GitHub의 특화된 로직을 처리하는 부분
        // GitHub에서 필요한 정보를 추출하고 처리합니다.
        log.info("GitHub User Info: {}", oAuth2User.getAttributes());
        log.info("userRequest.getAccessToken() = {}", userRequest.getAccessToken().getTokenValue());

        // 추가적인 사용자 정보 추출과 검증
        String accessToken = userRequest.getAccessToken().getTokenValue();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                "github",
                oAuth2User.getAttributes());

        if (oAuth2UserInfo instanceof EmailUpdatable emailUpdatable) {
            Email email = getGitHubEmail(accessToken);
            emailUpdatable.updateEmail(email);
        }

        return new OAuth2UserPrincipal(oAuth2UserInfo);
    }


    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId();

        String accessToken = userRequest.getAccessToken().getTokenValue();
        log.info("accessToken = {}", accessToken);

        // 카카오에서 id_token을 받아오는 로직 추가
        String idToken = (String) userRequest.getAdditionalParameters().get("id_token");
        String openId = (String) userRequest.getAdditionalParameters().get("open_id");

        log.info("userRequest.getAdditionalParameters() = {}", userRequest.getAdditionalParameters());
        log.info("oAuth2User.getName() = {}", oAuth2User.getName());
        log.info("idToken = {}", idToken);
        log.info("openId = {}", openId);

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                registrationId,
                oAuth2User.getAttributes());  // id_token 추가

        // OAuth2UserInfo 필드 값 검증
        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new Oauth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        return new OAuth2UserPrincipal(oAuth2UserInfo);
    }

    private Email getGitHubEmail(String accessToken) {
        String emailUri = "https://api.github.com/user/emails";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                emailUri, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );

        List<Map<String, Object>> emails = response.getBody();
        if (emails != null && !emails.isEmpty()) {
            return new Email((String) emails.get(0).get("email"));
        }

        return null;
    }
}
