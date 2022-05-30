package ru.itis.delivery_cube.handler.callback;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.itis.delivery_cube.TelegramBot;
import ru.itis.delivery_cube.handler.message.StartHandler;
import ru.itis.delivery_cube.model.Account;
import ru.itis.delivery_cube.model.AccountsRequests;
import ru.itis.delivery_cube.model.Request;
import ru.itis.delivery_cube.service.AccountService;
import ru.itis.delivery_cube.service.AccountsRequestsService;
import ru.itis.delivery_cube.service.RequestService;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class AddAdCallback {

    @Autowired
    private StartHandler startHandler;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private AccountsRequestsService accountsRequestsService;

    @SneakyThrows
    public void run(TelegramBot bot, CallbackQuery callbackQuery) {
        Account account = accountService.getAccountById(callbackQuery.getFrom().getId());
        Request request = requestService.getLastRequestByAccountId(account.getId());
        AccountsRequests accountsRequests = AccountsRequests.builder()
                .account(account)
                .request(request)
                .build();
        accountsRequestsService.save(accountsRequests);
        bot.execute(SendMessage.builder()
                .chatId(callbackQuery.getMessage().getChatId().toString())
                .text("DONE")
                .build());
        getMessage(bot, callbackQuery.getMessage());

    }

    @SneakyThrows
    private void getMessage(TelegramBot bot, Message message) {
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), "");
        sendMessage.setParseMode("html");
        File file = ResourceUtils.getFile("classpath:templates/start.html");
        String text = new String(Files.readAllBytes(file.toPath()));
        sendMessage.setText(text);

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow1 = new ArrayList<>();
        buttonRow1.add(InlineKeyboardButton.builder()
                .callbackData("/createAd")
                .text("Создать объявление")
                .build());
        buttonRow1.add(InlineKeyboardButton.builder()
                .callbackData("/listAd")
                .text("Созданные объявления")
                .build());
        buttons.add(buttonRow1);
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(buttons);
        sendMessage.setReplyMarkup(keyboard);
        bot.execute(sendMessage);
    }
}
