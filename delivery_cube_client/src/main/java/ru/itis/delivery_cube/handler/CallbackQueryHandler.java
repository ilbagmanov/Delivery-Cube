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
import ru.itis.delivery_cube.handler.callback.*;

@Component
public class CallbackQueryHandler {

    @Autowired
    private ListAdCallback listAdCallback;

    @Autowired
    private AddAdCallback addAdCallback;

    @Autowired
    private CreateAdCallback createAdCallback;

    @Autowired
    private AdNameCallback adNameCallback;

    @Autowired
    private AdDescriptionCallback adDescriptionCallback;

    @Autowired
    private AdCostCallback adCostCallback;

    @Autowired
    private AdFileCallback adFileCallback;

    @SneakyThrows
    public void catchCallback(TelegramBot bot, Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        bot.execute(AnswerCallbackQuery.builder().callbackQueryId(callbackQuery.getId()).build());
        switch (callbackQuery.getData()) {
            case "/createAd":
                createAdCallback.run(bot, update);
                break;
            case "/listAd":
                listAdCallback.run(bot, callbackQuery);
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
                adFileCallback.run(bot, callbackQuery);
                break;
            case "/addAd":
                addAdCallback.run(bot, callbackQuery);
                break;
            case "/backToMenu":
                break;
        }
        ;
    }
}
