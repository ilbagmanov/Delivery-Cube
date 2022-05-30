package ru.itis.delivery_cube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class DeliveryCubeApplication {

    public static void main(String[] args) throws TelegramApiException {
        SpringApplication.run(DeliveryCubeApplication.class, args);
    }

}
