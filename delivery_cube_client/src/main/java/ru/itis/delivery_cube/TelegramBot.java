package ru.itis.delivery_cube;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.itis.delivery_cube.handler.CallbackQueryHandler;
import ru.itis.delivery_cube.handler.MessageHandler;
import ru.itis.delivery_cube.service.AccountService;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Autowired
    private AccountService service;

    @Autowired
    private MessageHandler messageHandler;

    @Autowired
    private CallbackQueryHandler callbackQueryHandler;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            messageHandler.catchMessage(this, update);
        }
        if (update.hasCallbackQuery()) {
            callbackQueryHandler.catchCallback(this, update);
        }
    }
}
