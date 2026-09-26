package com.service;


import com.exception.EmailSendingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender mailSender;

    public void send(String from, String username, String subject, String text, byte[] attachment) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(from);
            helper.setTo(username);
            helper.setSubject(subject);
            helper.setText(text);

            if(attachment != null) {
                helper.addAttachment("task-report.pdf",
                        new ByteArrayResource(attachment)
                );
            }

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new EmailSendingException("failed to send email");
        }
    }
}
