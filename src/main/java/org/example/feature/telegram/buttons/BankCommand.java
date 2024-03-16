package org.example.feature.telegram.buttons;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class BankCommand  extends BotCommand {
    public BankCommand() {
        super("bank", "Банк");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        message.setText("Оберіть банк:");

        // Створюємо клавіатуру
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        // Створюємо кнопки
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton digitsButton = new InlineKeyboardButton();
        digitsButton.setText("ПриватБанк");
        digitsButton.setCallbackData("privatbank");
        row1.add(digitsButton);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton bankButton = new InlineKeyboardButton();
        bankButton.setText("МоноБанк");
        bankButton.setCallbackData("monobank");
        row2.add(bankButton);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        InlineKeyboardButton currencyButton = new InlineKeyboardButton();
        currencyButton.setText("НБУ");
        currencyButton.setCallbackData("nbu");
        row3.add(currencyButton);

        // Добавляємо кнопки
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboardMarkup.setKeyboard(keyboard);

        // Устанавливаем клавиатуру в сообщение
        message.setReplyMarkup(keyboardMarkup);

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
