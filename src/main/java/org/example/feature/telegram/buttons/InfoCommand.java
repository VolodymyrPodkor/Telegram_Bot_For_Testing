package org.example.feature.telegram.buttons;

import org.example.feature.currency.Currency;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InfoCommand extends BotCommand {
    public InfoCommand() {
        super("info", "Отримати інфо");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String text = "Актуальний курс валют в Приват Банку на (дата та час).";
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(Long.toString(chat.getId()));

        List<InlineKeyboardButton> buttons = Stream.of(Currency.USD, Currency.EUR)
                .map(Enum::name)
                .map(it -> InlineKeyboardButton.builder().text(it)
                        .callbackData(it).build())
                .collect(Collectors.toList());

        InlineKeyboardMarkup keyboard =
                InlineKeyboardMarkup.builder()
                        .keyboard(Collections.singleton(buttons))
                        .build();

        message.setReplyMarkup(keyboard);

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
