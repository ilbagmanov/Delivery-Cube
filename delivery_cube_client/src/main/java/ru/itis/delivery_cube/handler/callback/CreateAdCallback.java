package ru.itis.delivery_cube.handler.callback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.itis.delivery_cube.TelegramBot;
import ru.itis.delivery_cube.model.Request;
import ru.itis.delivery_cube.service.AccountService;
import ru.itis.delivery_cube.service.RequestService;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdCallback {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RequestService requestService;

    @SneakyThrows
    public void run(TelegramBot bot, Update update) {
        File file = ResourceUtils.getFile("classpath:templates/CreateAd.html");
        String ans = new String(Files.readAllBytes(file.toPath()));
        Long id = requestService.save(Request.builder().authorId(accountService.getAccountById(update.getCallbackQuery().getFrom().getId())).build());
        ans = String.format(ans, id, "🚫", "🚫", "🚫", "🚫");

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


        bot.execute(EditMessageText.builder()
                .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .parseMode("html")
                .text(ans)
                .build());
        bot.execute(EditMessageReplyMarkup.builder()
                .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .replyMarkup(new InlineKeyboardMarkup(keyboard))
                .build());
    }
}
