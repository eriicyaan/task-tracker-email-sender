package com.configuration;


import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final Environment environment;


    private Map<String, Object> getConsumerConfig() {
        HashMap<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.consumer.bootstrap-servers"));
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, environment.getProperty("spring.kafka.consumer.group-id"));

        return config;
    }


    @Bean
    ConsumerFactory<UUID, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getConsumerConfig());
    }


    @Bean
    ConcurrentKafkaListenerContainerFactory<UUID, Object> kafkaListenerContainerFactory(ConsumerFactory<UUID, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<UUID, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        return factory;
    }

}
