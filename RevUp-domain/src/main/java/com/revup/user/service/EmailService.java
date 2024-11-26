package com.revup.user.service;

import com.revup.user.dto.EmailCertificationNumberInfo;

public interface EmailService {

    void sendMail(EmailCertificationNumberInfo info);
}
