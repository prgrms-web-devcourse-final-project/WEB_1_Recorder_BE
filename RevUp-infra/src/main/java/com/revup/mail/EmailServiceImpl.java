package com.revup.mail;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.user.dto.EmailCertificationNumberInfo;
import com.revup.user.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendMail(EmailCertificationNumberInfo info) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(info.setTo().value());
            helper.setSubject(info.subject());
            helper.setText("인증번호는 " + info.number().value() +"입니다.", true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException(ErrorCode.UNKNOWN_EXCEPTION);
        }
    }
}
