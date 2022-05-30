package ru.itis.delivery_cube.handler.callback;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.itis.delivery_cube.TelegramBot;
import ru.itis.delivery_cube.model.Account;
import ru.itis.delivery_cube.model.AccountsRequests;
import ru.itis.delivery_cube.model.Request;
import ru.itis.delivery_cube.service.AccountService;
import ru.itis.delivery_cube.service.AccountsRequestsService;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ListAdCallback {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountsRequestsService accountsRequestsService;

    @SneakyThrows
    public void run(TelegramBot bot, CallbackQuery callbackQuery) {
        List<AccountsRequests> requests = accountsRequestsService.getAllByAccount();
        int i = ((int)(Math.random() * 100)) % requests.size();
        AccountsRequests accountsRequests = requests.get(i);
        Request request = accountsRequests.getRequest();

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow1 = new ArrayList<>();
        buttonRow1.add(InlineKeyboardButton.builder()
                .callbackData("/next")
                .text("Другое")
                .build());
        buttons.add(buttonRow1);

        bot.execute(SendPhoto.builder()
                .chatId(callbackQuery.getMessage().getChatId().toString())
                .parseMode("html")
                .replyMarkup(new InlineKeyboardMarkup(buttons))
                .photo(new InputFile(request.getFile()))
                .caption(getAdMessage(request))
                .build());

    }

    @SneakyThrows
    private String getAdMessage(Request request) {
        File file = ResourceUtils.getFile("classpath:templates/CreateAd.html");
        String ans = new String(Files.readAllBytes(file.toPath()));
        return String.format(ans, request.getId(), request.getName(), request.getDescription(), request.getCost(), request.getFile() != null ? "✅" : "🚫");
    }
}
