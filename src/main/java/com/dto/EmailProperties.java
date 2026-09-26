package com.dto;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "email")
public record EmailProperties(
        String from,
        String welcomeSubject,
        String welcomeText,
        String reportSubject,
        String reportText) {
}
