package com.revup.mail;

import com.revup.user.dto.CertificationNumber;
import com.revup.user.dto.EmailCertificationNumberInfo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender mailSender;

    @Test
    @DisplayName("이메일 전송 성공")
    void sendMail_ShouldSendEmailSuccessfully() throws IOException, MessagingException {
        // Arrange
        String to = "test@example.com";
        String number = "1234";
        String subject = "Test Subject";

        MimeMessage mockMessage = Mockito.mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mockMessage);
        when(mockMessage.getContent()).thenReturn("<p>"+number+"</p>"); // 반환값 설정

        // Act
        EmailCertificationNumberInfo info = EmailCertificationNumberInfo.of(
                to,
                new CertificationNumber(number),
                subject);
        emailService.sendMail(info);

        //Assert
        ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        MimeMessage sentMessage = captor.getValue();
        Object content = sentMessage.getContent();
        assertThat(content).asString().contains(number);
    }
}