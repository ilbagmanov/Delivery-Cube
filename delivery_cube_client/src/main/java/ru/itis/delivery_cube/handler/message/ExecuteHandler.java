package ru.itis.delivery_cube.handler.message;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.itis.delivery_cube.TelegramBot;
import ru.itis.delivery_cube.model.Account;
import ru.itis.delivery_cube.model.Request;
import ru.itis.delivery_cube.service.AccountService;
import ru.itis.delivery_cube.service.RequestService;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class ExecuteHandler {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RequestService requestService;

    @SneakyThrows
    public void run(TelegramBot bot, Message message) {
        Account account = accountService.getAccountById(message.getFrom().getId());
        Request request;
        Integer status = account.getStatus();
        switch (status) {
            case 1:
                request = requestService.getLastRequestByAccountId(account.getId());
                request.setName(message.getText());
                requestService.save(request);
                accountService.updateAccountById(account.getId(), 0);
                sendAdMessage(bot, message, request);
                break;
            case 2:
                request = requestService.getLastRequestByAccountId(account.getId());
                request.setDescription(message.getText());
                requestService.save(request);
                accountService.updateAccountById(account.getId(), 0);
                sendAdMessage(bot, message, request);
                break;
            case 3:
                request = requestService.getLastRequestByAccountId(account.getId());
                request.setCost(Double.valueOf(message.getText()));
                requestService.save(request);
                accountService.updateAccountById(account.getId(), 0);
                sendAdMessage(bot, message, request);
                break;
            default:
                bot.execute(SendMessage.builder()
                        .chatId(message.getChatId().toString())
                        .text("ERROR")
                        .build());
                break;
        }


    }

    @SneakyThrows
    private void sendAdMessage(TelegramBot bot, Message message, Request request) {
        bot.execute(SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(getAdMessage(request))
                .parseMode("html")
                .replyMarkup(new InlineKeyboardMarkup(getKeyboard()))
                .build());
    }

    @SneakyThrows
    private String getAdMessage(Request request) {
        File file = ResourceUtils.getFile("classpath:templates/CreateAd.html");
        String ans = new String(Files.readAllBytes(file.toPath()));
        return String.format(ans, request.getId(), request.getName(), request.getDescription(), request.getCost(), "🚫");
    }

    private List<List<InlineKeyboardButton>> getKeyboard() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new LinkedList<>();
        row1.add(InlineKeyboardButton.builder()
                .callbackData("/adName")
                .text("Edit name")
                .build());
        row1.add(InlineKeyboardButton.builder()
                .callbackData("/adDescription")
                .text("Edit description")
                .build());
        keyboard.add(row1);
        row1 = new LinkedList<>();
        row1.add(InlineKeyboardButton.builder()
                .callbackData("/adCost")
                .text("Edit cost")
                .build());
        row1.add(InlineKeyboardButton.builder()
                .callbackData("/adFile")
                .text("Edit file")
                .build());
        keyboard.add(row1);
        row1 = new LinkedList<>();
        row1.add(InlineKeyboardButton.builder()
                .callbackData("/backToMain")
                .text("« Назад")
                .build());
        keyboard.add(row1);
        return keyboard;
    }
}
