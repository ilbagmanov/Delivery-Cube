package ru.itis.delivery_cube.handler.callback;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.itis.delivery_cube.TelegramBot;
import ru.itis.delivery_cube.model.Account;
import ru.itis.delivery_cube.model.AccountsRequests;
import ru.itis.delivery_cube.model.Request;
import ru.itis.delivery_cube.service.AccountService;
import ru.itis.delivery_cube.service.AccountsRequestsService;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Component
public class ListAdCallback {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountsRequestsService accountsRequestsService;

    @SneakyThrows
    public void run(TelegramBot bot, CallbackQuery callbackQuery) {
        Account account = accountService.getAccountById(callbackQuery.getFrom().getId());
        List<AccountsRequests> requests = accountsRequestsService.getAllByAccount(account);
        for (AccountsRequests accountsRequests : requests) {
            Request request = accountsRequests.getRequest();
            bot.execute(SendPhoto.builder()
                    .chatId(callbackQuery.getMessage().getChatId().toString())
                    .parseMode("html")
                    .photo(new InputFile(request.getFile()))
                    .caption(getAdMessage(request))
                    .build());
        }
    }

    @SneakyThrows
    private String getAdMessage(Request request) {
        File file = ResourceUtils.getFile("classpath:templates/CreateAd.html");
        String ans = new String(Files.readAllBytes(file.toPath()));
        return String.format(ans, request.getId(), request.getName(), request.getDescription(), request.getCost(), request.getFile() != null ? "✅" : "🚫");
    }
}
