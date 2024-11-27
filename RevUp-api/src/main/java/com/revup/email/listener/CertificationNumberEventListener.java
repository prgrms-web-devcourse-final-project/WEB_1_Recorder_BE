package com.revup.email.listener;

import com.revup.user.dto.EmailCertificationNumberInfo;
import com.revup.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CertificationNumberEventListener {

    private final EmailService emailService;

    @Async
    @EventListener
    public void certificationNumberEventListener(
            EmailCertificationNumberInfo info
    ) {
        emailService.sendMail(info);
    }
}
