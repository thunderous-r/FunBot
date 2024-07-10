package org.hangman;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

public class Bot extends TelegramLongPollingBot {

    private final String helloMessage = "Добро пожаловать\n" +
            "/hangman - начать новую игру в Виселицу\n" +
            "/bullscows - начать новую игру в Быки и Коровы";
    HashMap<Long, HangMan> hangmanPlayers = new HashMap<>();
    HashMap<Long, BullsCows> bullsCowsPlayers = new HashMap<>();

    @Override
    public String getBotUsername() {
        return "JustForFunS";
    }

    @Override
    public String getBotToken() {
        Properties prop = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            prop.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop.getProperty("token");
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);
        var msg = update.getMessage();
        var user = msg.getFrom();
        var id = user.getId();

        if (msg.isCommand()) {
            if (msg.getText().equals("/start")) {
                sendText(id, helloMessage);
                if (bullsCowsPlayers.containsKey(id)) {
                    bullsCowsPlayers.get(id).stopGame();
                    bullsCowsPlayers.remove(id);
                }
                if (hangmanPlayers.containsKey(id)) {
                    hangmanPlayers.get(id).stopGame();
                    hangmanPlayers.remove(id);
                }
            } else if (msg.getText().equals("/hangman")) {
                if (bullsCowsPlayers.containsKey(id)) {
                    bullsCowsPlayers.get(id).stopGame();
                    bullsCowsPlayers.remove(id);
                }
                if (!hangmanPlayers.containsKey(id)) {
                    hangmanPlayers.put(id, new HangMan());
                    sendText(id, hangmanPlayers.get(id).newGame());
                } else {
                    sendText(id, hangmanPlayers.get(id).newGame());
                }
            } else if (msg.getText().equals("/bullscows")) {
                if (hangmanPlayers.containsKey(id)) {
                    hangmanPlayers.get(id).stopGame();
                    hangmanPlayers.remove(id);
                }
                if (!bullsCowsPlayers.containsKey(id)) {
                    bullsCowsPlayers.put(id, new BullsCows());
                    sendText(id, bullsCowsPlayers.get(id).newGame());
                } else {
                    sendText(id, bullsCowsPlayers.get(id).newGame());
                }
            } else if (msg.getText().equals("/rules")) {
                if (bullsCowsPlayers.containsKey(id) && bullsCowsPlayers.get(id).isPlaying()){
                    sendText(id, bullsCowsPlayers.get(id).rules());
                } else if (hangmanPlayers.containsKey(id) && hangmanPlayers.get(id).isPlaying()){
                    sendText(id, hangmanPlayers.get(id).rules());
                } else {
                    sendText(id, "Для использования этой команды - начни игру");
                }
            }
        }
        if (!msg.isCommand()) {
            if (hangmanPlayers.containsKey(id) && hangmanPlayers.get(id).isPlaying()) {
                sendText(id, hangmanPlayers.get(id).hang(msg.getText()));
            } else if (bullsCowsPlayers.containsKey(id) && bullsCowsPlayers.get(id).isPlaying()){
                sendText(id, bullsCowsPlayers.get(id).guess(msg.getText()));
            } else {
                sendText(id, "Что будем делать дальше? Нажми:\n" +
                        "/hangman - начать новую игру в Виселицу\n" +
                        "/bullscows - начать новую игру в Быки-Коровы");
            }
        }
    }

    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .parseMode(ParseMode.HTML)
                .text(what).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
