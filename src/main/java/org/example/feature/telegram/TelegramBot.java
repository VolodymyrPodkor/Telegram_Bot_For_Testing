package org.example.feature.telegram;

import org.example.feature.currency.*;
import org.example.feature.telegram.buttons.BankCommand;
import org.example.feature.telegram.buttons.InfoCommand;
import org.example.feature.telegram.buttons.SettingsCommand;
import org.example.feature.telegram.command.StartCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TelegramBot extends TelegramLongPollingCommandBot {
    public TelegramBot() {
        register(new StartCommand());
        register(new InfoCommand());
        register(new SettingsCommand());
        register(new BankCommand());
        // register(new HelpCommand());

    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            String callbackQuery = update.getCallbackQuery().getData();

            SendMessage responseMessage = new SendMessage();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            responseMessage.setChatId(Long.toString(chatId));

            if ("info".equals(callbackQuery)) {

                String text = "Актуальний курс валют в Приват Банку на (дата та час).";
                responseMessage.setText(text);

                List<InlineKeyboardButton> buttons = Stream.of(Currency.USD, Currency.EUR)
                        .map(Enum::name)
                        .map(it -> InlineKeyboardButton.builder().text(it)
                                .callbackData(it).build())
                        .collect(Collectors.toList());

                InlineKeyboardMarkup keyboard =
                        InlineKeyboardMarkup.builder()
                                .keyboard(Collections.singleton(buttons))
                                .build();

                responseMessage.setReplyMarkup(keyboard);
            } else if ("settings".equals(callbackQuery)) {

                String text = "Виберіть налаштування:";
                responseMessage.setText(text);

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
                backButton.setText("Повернутись на головне меню");
                backButton.setCallbackData("/start");
                row5.add(backButton);

                // Добавляємо кнопки
                List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
                keyboard.add(row2);
                keyboard.add(row3);
                keyboard.add(row1);
                keyboard.add(row4);
                keyboard.add(row5);
                keyboardMarkup.setKeyboard(keyboard);

                responseMessage.setReplyMarkup(keyboardMarkup);
            }
            try {
                execute(responseMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public String getBotToken() {
        return BotConstants.BOT_TOKEN;
    }


    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }
}