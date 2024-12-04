package com.revup.user.model.mapper;

import com.revup.annotation.Mapper;
import com.revup.user.dto.CertificationKey;
import com.revup.user.dto.CertificationNumber;
import com.revup.user.dto.EmailCertificationEventInfo;
import com.revup.user.entity.Affiliation;
import com.revup.user.entity.Profile;
import com.revup.user.entity.User;
import com.revup.user.model.response.UpdateAffiliationResponse;
import com.revup.user.model.response.UpdateProfileResponse;
import com.revup.user.model.response.UserProfileResponse;
import com.revup.user.model.response.ValidateEmailResponse;

@Mapper
public class UserMapper {

    public UserProfileResponse toUserProfileResponse(User user) {
        return UserProfileResponse.of(user);
    }

    public UpdateProfileResponse toUpdateProfileResponse(Profile profile) {
        return UpdateProfileResponse.of(profile);
    }

    public UpdateAffiliationResponse toUpdateEmailResponse(Affiliation affiliation) {
        return UpdateAffiliationResponse.of(affiliation);
    }

    public EmailCertificationEventInfo toEmailCertificationNumberInfo(
            String setTo, CertificationNumber number
    ) {
        return EmailCertificationEventInfo.of(
                setTo,
                number,
                "[RevUp] 이메일 인증 번호입니다."
        );
    }

    public ValidateEmailResponse toValidateResponse(CertificationKey key) {
        return new ValidateEmailResponse(key.key());
    }
}
