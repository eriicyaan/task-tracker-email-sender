package com.service;


import com.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${mail.from}")
    private String from;

    private final JavaMailSender mailSender;

    private final String WELCOME_SUBJECT = """
            Добро пожаловать в Task Tracker! 🎉
            """;
    private final String WELCOME_TEXT = """
            Привет!
            
            Добро пожаловать в Task Tracker — твой личный помощник для управления задачами.
            
            Здесь ты можешь создавать задачи, следить за их выполнением и не держать всё в голове. А мы постараемся вовремя напоминать о том, что действительно важно.
            
            Желаем продуктивности, порядка в делах и побольше выполненных задач! 🚀
            
            С уважением,
            Команда Task Tracker
            """;

    public void send(UserCreatedEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(event.getUsername());
        message.setSubject(WELCOME_SUBJECT);
        message.setText(WELCOME_TEXT);

        mailSender.send(message);
    }
}
