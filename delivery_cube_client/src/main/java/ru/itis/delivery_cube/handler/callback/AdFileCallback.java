package ru.itis.delivery_cube.handler.callback;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.itis.delivery_cube.TelegramBot;
import ru.itis.delivery_cube.service.AccountService;

import java.io.File;
import java.nio.file.Files;

@Component
public class AdFileCallback {

    @Autowired
    private AccountService accountService;

    @SneakyThrows
    public void run(TelegramBot bot, CallbackQuery callbackQuery) {
        File file = ResourceUtils.getFile("classpath:templates/AddFile.html");
        String ans = new String(Files.readAllBytes(file.toPath()));
        accountService.updateAccountById(callbackQuery.getFrom().getId(), 4);
        bot.execute(SendMessage.builder()
                .chatId(callbackQuery.getMessage().getChatId().toString())
                .parseMode("html")
                .text(ans)
                .build());
    }
}
