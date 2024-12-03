package com.revup.user.service;

import com.revup.auth.dto.token.TokenInfo;
import com.revup.user.adaptor.EmailDomainAdaptor;
import com.revup.user.adaptor.UserAdaptor;
import com.revup.user.dto.Certification;
import com.revup.user.dto.CertificationKey;
import com.revup.user.dto.CertificationNumber;
import com.revup.user.dto.Email;
import com.revup.user.entity.Affiliation;
import com.revup.user.entity.EmailDomain;
import com.revup.user.entity.Profile;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDomainService implements UserService {

    private final UserAdaptor userAdaptor;
    private final EmailDomainAdaptor emailDomainAdaptor;
    private final CertificationService certificationService;

    @Override
    public User findById(Long id) {
        return userAdaptor.findById(id);
    }

    @Override
    public User findByTokenInfo(TokenInfo tokenInfo) {
        return userAdaptor.findByTokenClaim(
                tokenInfo.socialId(),
                tokenInfo.loginType()
        );
    }

    @Override
    public User register(User user) {
        return userAdaptor.save(user);
    }

    @Override
    public Profile updateProfile(User user, Profile profile) {
        user.updateProfile(profile);
        return user.getProfile();
    }

    @Override
    @Transactional
    public Affiliation updateEmail(User user, Certification certification, Email email) {
        CertificationKey key = certification.key();
        CertificationNumber inputNumber = certification.number();
        certificationService.validateCertificationNumber(key, inputNumber);

        String domain = extractDomain(email);
        EmailDomain findDomain = emailDomainAdaptor.findByDomain(domain);

        Affiliation affiliation = Affiliation.of(email, findDomain);
        user.updateAffiliation(affiliation);

        certificationService.deleteNumber(key);
        return user.getAffiliation();
    }

    private String extractDomain(Email email) {
        String value = email.value();
        return value.substring(value.indexOf("@")+1);
    }
}
