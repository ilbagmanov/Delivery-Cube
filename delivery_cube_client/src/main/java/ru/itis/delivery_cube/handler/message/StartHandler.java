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
import ru.itis.delivery_cube.dto.RegistrationForm;
import ru.itis.delivery_cube.service.AccountService;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class StartHandler {

    @Autowired
    private AccountService accountService;

    @SneakyThrows
    public void run(final TelegramBot bot, Message message) {
        accountService.registerAccount(new RegistrationForm(message.getFrom().getId(), message.getFrom().getUserName()));

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
