package com.revup.user.service;

import com.revup.annotation.UseCase;
import com.revup.user.dto.CertificationKey;
import com.revup.user.dto.CertificationNumber;
import com.revup.user.dto.EmailCertificationNumberInfo;
import com.revup.user.model.mapper.UserMapper;
import com.revup.user.model.request.ValidateEmailRequest;
import com.revup.user.model.response.ValidateEmailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@UseCase
@RequiredArgsConstructor
public class ValidateEmailUseCase {

    private final CertificationService certificationService;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 이메일 인증하고 업데이트 하는 방식.
     * 이메일로 인증번호를 보내는 작업을 비동기로 먼저 실행 후 redis에 저장
     * @param request
     * @return
     */
    public ValidateEmailResponse execute(ValidateEmailRequest request) {
        CertificationNumber number = certificationService.createNumber();
        EmailCertificationNumberInfo info = userMapper.toEmailCertificationNumberInfo(
                request.email(),
                number
        );
        eventPublisher.publishEvent(info);
        CertificationKey key = certificationService.saveCertificationNumber(number);
        return userMapper.toValidateResponse(key);
    }
}
