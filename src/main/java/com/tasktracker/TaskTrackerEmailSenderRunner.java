package com.tasktracker;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TaskTrackerEmailSenderRunner {
    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerEmailSenderRunner.class, args);
    }
}