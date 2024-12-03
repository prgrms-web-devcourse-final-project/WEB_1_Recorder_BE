package com.revup.user.adaptor;

import com.revup.annotation.Adaptor;
import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.user.entity.EmailDomain;
import com.revup.user.repository.EmailDomainRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class EmailDomainAdaptor {

    private final EmailDomainRepository emailDomainRepository;
    public EmailDomain findByDomain(String domain) {
        return emailDomainRepository.findByDomain(domain)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_DOMAIN_NOT_FOUND));
    }
}
