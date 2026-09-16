package com.handler;


import com.event.UserCreatedEvent;
import com.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "user-created-event-topic", groupId = "user-created-event")
@RequiredArgsConstructor
public class UserCreatedEventHandler {

    private final EmailService emailService;


    @KafkaHandler
    public void handle(UserCreatedEvent event) {
        emailService.send(event);
    }

}
