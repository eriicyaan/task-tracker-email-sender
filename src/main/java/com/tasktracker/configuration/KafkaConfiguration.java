package com.tasktracker.configuration;


import com.tasktracker.kafka.events.EmailSendingEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final Environment environment;


    private Map<String, Object> getConsumerConfig() {
        HashMap<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.bootstrap-servers"));
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, environment.getProperty("spring.kafka.consumer.group-id"));
        config.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, environment.getProperty("spring.kafka.consumer.trusted-packages"));

        return config;
    }


    @Bean
    ConsumerFactory<UUID, EmailSendingEvent> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getConsumerConfig());
    }


    @Bean
    ConcurrentKafkaListenerContainerFactory<UUID, EmailSendingEvent> kafkaListenerContainerFactory(ConsumerFactory<UUID, EmailSendingEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<UUID, EmailSendingEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(0L, 0L)));
        return factory;
    }


    @Bean
    NewTopic emailSendingTasksTopic() {
        return TopicBuilder
                .name("email-sending-tasks")
                .partitions(3)
                .build();
    }
}
