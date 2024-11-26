package com.revup.user.service;

import com.revup.annotation.UseCase;
import com.revup.user.dto.CertificationKey;
import com.revup.user.dto.CertificationNumber;
import com.revup.user.entity.User;
import com.revup.user.model.mapper.UserMapper;
import com.revup.user.model.request.UpdateEmailRequest;
import com.revup.user.model.response.UpdateEmailResponse;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateEmailUseCase {

    private final CertificationService certificationService;
    private final UserUpdater userUpdater;
    private final UserMapper userMapper;

    public UpdateEmailResponse execute(UpdateEmailRequest request) {
        CertificationKey key = request.toCertificationKey();
        CertificationNumber inputNumber = request.toCertificationNumber();
        certificationService.validateCertificationNumber(key, inputNumber);
        certificationService.deleteNumber(key);
        User user = userUpdater.updateEmail(request.toEmail());
        return userMapper.toUpdateEmailResponse(user);
    }
}
