package com.revup.user.service.port.out;

import com.revup.user.dto.EmailCertificationEventInfo;

public interface EmailService {

    void sendMail(EmailCertificationEventInfo info);
}
