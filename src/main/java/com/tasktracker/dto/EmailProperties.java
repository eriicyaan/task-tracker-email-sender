package com.tasktracker.dto;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mail")
public record EmailProperties(String from, Message welcome, Message report) {

    public record Message(String subject, String text) {

    }
}
