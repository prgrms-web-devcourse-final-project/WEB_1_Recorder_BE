package com.revup.user.model.mapper;

import com.revup.annotation.Mapper;
import com.revup.user.dto.CertificationKey;
import com.revup.user.dto.CertificationNumber;
import com.revup.user.dto.EmailCertificationNumberInfo;
import com.revup.user.entity.User;
import com.revup.user.model.response.UpdateEmailResponse;
import com.revup.user.model.response.UpdateProfileResponse;
import com.revup.user.model.response.ValidateEmailResponse;

@Mapper
public class UserMapper {

    public UpdateProfileResponse toUpdateProfileResponse(User user) {
        return new UpdateProfileResponse(
                user.getProfile().getNickname(),
                user.getProfile().getProfileImage(),
                user.getBusinessEmail()
        );
    }

    public UpdateEmailResponse toUpdateEmailResponse(User user) {
        return new UpdateEmailResponse(
                user.getBusinessEmail()
        );
    }

    public EmailCertificationNumberInfo toEmailCertificationNumberInfo(
            String setTo, CertificationNumber number
    ) {
        return EmailCertificationNumberInfo.of(
                setTo,
                number,
                "[RevUp] 이메일 인증 번호입니다."
        );
    }

    public ValidateEmailResponse toValidateResponse(CertificationKey key) {
        return new ValidateEmailResponse(key.key());
    }
}
