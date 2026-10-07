# Task Tracker Email Sender

Микросервис для отправки email-уведомлений пользователям.

Сервис получает задачи на отправку писем из Kafka и отправляет их через SMTP.

## Возможности

* Получение сообщений из Kafka
* Отправка приветственных писем
* Отправка ежедневных отчётов
* Отправка email через SMTP

## Технологии

**Backend:**

* Java
* Spring Boot
* Spring Mail
* Maven

**Messaging:**

* Apache Kafka
* Spring Kafka

**DevOps:**

* Docker
* Docker Compose

## Клонирование репозитория

```bash

git clone https://github.com/eriicyaan/task-tracker-email-sender.git
```

## Запуск

Для запуска всего проекта используйте инфраструктурный репозиторий:

[task-tracker-infrastructure](https://github.com/eriicyaan/task-tracker-infrastructure)
