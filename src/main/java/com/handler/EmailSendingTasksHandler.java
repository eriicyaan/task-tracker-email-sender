package com.handler;


import com.dto.EmailProperties;
import com.event.UserCreatedEvent;
import com.event.UserReportCreatedEvent;
import com.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@KafkaListener(topics = "email-sending-tasks", groupId = "email-sending-tasks-group")
@RequiredArgsConstructor
public class EmailSendingTasksHandler {

    private final EmailService emailService;

    private final EmailProperties emailProperties;

    @KafkaHandler
    public void handleEvent(Object message) throws IOException {
        if(message.getClass().equals(UserCreatedEvent.class)) {
            UserCreatedEvent userCreatedEvent = (UserCreatedEvent) message;

            emailService.send(
                    emailProperties.from(),
                    userCreatedEvent.getUsername(),
                    emailProperties.welcomeSubject(),
                    emailProperties.welcomeText(),
                    null
            );
        } else {
            UserReportCreatedEvent userReportCreatedEvent = (UserReportCreatedEvent) message;
            InputStreamResource report = userReportCreatedEvent.getReport();

            byte[] attachment = report.getContentAsByteArray();


            emailService.send(
                    emailProperties.from(),
                    userReportCreatedEvent.getUsername(),
                    emailProperties.reportSubject(),
                    emailProperties.reportText(),
                    attachment
            );
        }
    }

}
