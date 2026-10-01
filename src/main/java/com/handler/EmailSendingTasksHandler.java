package com.handler;


import com.dto.EmailProperties;
import com.kafka.events.EmailSendingEvent;
import com.kafka.events.EventType;
import com.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "email-sending-tasks", groupId = "email-sending-tasks-group")
@RequiredArgsConstructor
public class EmailSendingTasksHandler {

    private final EmailService emailService;

    private final EmailProperties emailProperties;

    @KafkaHandler
    public void handleEvent(EmailSendingEvent message) {
        log.info("RECEIVE MESSAGE: {}", message);

        switch (message.getEventType()) {
            case USER_CREATED -> emailService.send(
                    emailProperties.from(),
                    message.getUsername(),
                    emailProperties.welcome().subject(),
                    emailProperties.welcome().text(),
                    null
            );
            case USER_REPORT_CREATED -> emailService.send(
                    emailProperties.from(),
                    message.getUsername(),
                    emailProperties.report().subject(),
                    emailProperties.report().text(),
                    message.getReport()
            );
        }

        log.info("SUCCESSFULLY SEND MESSAGE TO EMAIL");
    }

}
