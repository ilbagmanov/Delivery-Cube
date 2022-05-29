package ru.itis.delivery_cube.handler;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.itis.delivery_cube.TelegramBot;
import ru.itis.delivery_cube.handler.callback.AdCostCallback;
import ru.itis.delivery_cube.handler.callback.AdDescriptionCallback;
import ru.itis.delivery_cube.handler.callback.AdNameCallback;
import ru.itis.delivery_cube.handler.callback.CreateAdCallback;

@Component
public class CallbackQueryHandler {

    @Autowired
    private CreateAdCallback createAdCallback;

    @Autowired
    private AdNameCallback adNameCallback;

    @Autowired
    private AdDescriptionCallback adDescriptionCallback;

    @Autowired
    private AdCostCallback adCostCallback;

    @SneakyThrows
    public void catchCallback(TelegramBot bot, Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        bot.execute(AnswerCallbackQuery.builder().callbackQueryId(callbackQuery.getId()).build());
        switch (callbackQuery.getData()) {
            case "/createAd":
                createAdCallback.run(bot, update);
                break;
            case "/listAd":
                break;
            case "/adName":
                adNameCallback.run(bot, callbackQuery);
                break;
            case "/adDescription":
                adDescriptionCallback.run(bot, callbackQuery);
                break;
            case "/adCost":
                adCostCallback.run(bot, callbackQuery);
                break;
            case "/adFile":
                break;
            case "/backToMenu":
                break;
        }
        ;
    }
}
