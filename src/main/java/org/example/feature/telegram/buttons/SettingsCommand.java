package org.example.feature.telegram.buttons;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;

public class SettingsCommand extends BotCommand {
    public SettingsCommand() {
        super("settings", "Налаштування");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        message.setText("Виберіть налаштування:");

        // Створюємо клавіатуру
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        // Створюємо кнопки
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton bankButton = new InlineKeyboardButton();
        bankButton.setText("Банк");
        bankButton.setCallbackData("bank");
        row2.add(bankButton);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        InlineKeyboardButton currencyButton = new InlineKeyboardButton();
        currencyButton.setText("Валюта");
        currencyButton.setCallbackData("currency");
        row3.add(currencyButton);

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton digitsButton = new InlineKeyboardButton();
        digitsButton.setText("Кількість знаків після коми");
        digitsButton.setCallbackData("digits");
        row1.add(digitsButton);

        List<InlineKeyboardButton> row4 = new ArrayList<>();
        InlineKeyboardButton timeButton = new InlineKeyboardButton();
        timeButton.setText("Час сповіщень");
        timeButton.setCallbackData("time");
        row4.add(timeButton);

        List<InlineKeyboardButton> row5 = new ArrayList<>();
        InlineKeyboardButton backButton = new InlineKeyboardButton();
        timeButton.setText("Повернутись на головне меню");
        timeButton.setCallbackData("start");
        row5.add(backButton);

        // Добавляємо кнопки
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        keyboard.add(row5);
        keyboardMarkup.setKeyboard(keyboard);


        message.setReplyMarkup(keyboardMarkup);

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}