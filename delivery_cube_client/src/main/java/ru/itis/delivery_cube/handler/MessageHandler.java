package ru.itis.delivery_cube.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.itis.delivery_cube.TelegramBot;
import ru.itis.delivery_cube.handler.message.ExecuteHandler;
import ru.itis.delivery_cube.handler.message.StartHandler;

@Component
public class MessageHandler {

    @Autowired
    private StartHandler startHandler;

    @Autowired
    private ExecuteHandler executeHandler;

    public void catchMessage(TelegramBot bot, Update update){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        switch (message.getText()) {
            case "/start":
                startHandler.run(bot, message);
                break;
            default:
                executeHandler.run(bot, message);
        }
    }

    private void executeHandler(TelegramBot bot, Message message) {
    }
}
