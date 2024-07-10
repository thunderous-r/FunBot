package org.hangman;

import java.util.ArrayList;
import java.util.Collections;

public class HangMan {

    private final String RULES = "<b>Правила игры:</b>\n" +
            "Загадано слово (существительное, нарицательное в именительном падеже единственного числа, либо " +
            "множественного числа при отсутствии у слова формы единственного числа). Ты можешь предложить букву, " +
            "которая может входить в это слово. Если предложенная буква есть в слове, то в ответ придёт сообщение " +
            "с указанием этой буквы (или букв, если их несколько) в слове. Если буквы нет - добавляется следующий " +
            "элемент виселицы. В этой игре у тебя будет всего 7 попыток, чтобы угадать слово (попытки сгорают " +
            "только при вводе буквы, которой в слове нет).";

    private final String[] hangManListEasy = {"Указатель", "Радуга", "Мармелад", "Поиск", "Прятки", "Сторож", "Копейка", "Леопард",
            "Аттракцион", "Дрессировка", "Ошейник", "Карамель", "Водолаз", "Защита", "Батарея", "Решётка", "Квартира",
            "Дельфинарий", "Непогода", "Вход", "Полиция", "Перекрёсток", "Башня", "Стрелка", "Градусник", "Бутылка",
            "Щипцы", "Наволочка", "Павлин", "Карточка", "Записка", "Лестница", "Переулок", "Сенокос", "Рассол", "Закат",
            "Сигнализация", "Журнал", "Заставка", "Тиранозавр", "Микрофон", "Прохожий", "Квитанция", "Пауза", "Новости",
            "Скарабей", "Серебро", "Творог", "Осадок", "Песня", "Корзина", "Сдача", "Овчарка", "Хлопья", "Телескоп", "Микроб",
            "Угощение", "Экскаватор", "Письмо", "Пришелец", "Айсберг", "Пластик", "Доставка", "Полка", "Билет", "Вторник",
            "Льдина", "Интерес", "Троллейбус", "Футболист", "Лисёнок", "Пример", "Баклажан", "Лягушка", "Джокер", "Котлета",
            "Накидка", "Дикобраз", "Барбарис", "Работник", "Кристалл", "Доспех", "Халва", "Велосипед", "Крючок", "Кочка",
            "Черепаха", "Петля", "Осень", "Яйцо"};
    private String word;
    private String asterisk;
    private int count;

    private boolean playing = false;

    private ArrayList<String> usedLetters = new ArrayList<>();

    public String newGame() {
        word = setWord();
        usedLetters.clear();
        asterisk = new String(new char[word.length()]).replace("\0", "*");
        count = 0;
        playing = true;
        return "Я загадал слово из "+ word.length() + " букв.\n\n" +
                "Попробуй угадать букву в слове \n" + asterisk + "\n" +
                "Нажми /rules, чтобы посмотреть правила игры.";
    }

    private String setWord() {
        return hangManListEasy[(int) (Math.random() * hangManListEasy.length) + 1];
    }


    public String hang(String guess) {
        String newasterisk = "";
        if (usedLetters.contains(guess.toLowerCase())) {
            return "Буква \"" + guess + "\" уже была использована, укажи другую букву.\n" +
                    "Были использованы буквы: " + usedLetters;
        } else {
            usedLetters.add(guess.toLowerCase());
            Collections.sort(usedLetters);
        }
        for (int i = 0; i < word.length(); i++) {
            if (word.toLowerCase().charAt(i) == guess.toLowerCase().charAt(0)) {
                if (i == 0) {
                    newasterisk += guess.toUpperCase().charAt(0);
                } else {
                    newasterisk += guess.toLowerCase().charAt(0);
                }
            } else if (asterisk.charAt(i) != '*') {
                newasterisk += word.charAt(i);
            } else {
                newasterisk += "*";
            }
        }

        if (asterisk.equals(newasterisk)) {
            count++;
            return drawHangman();
        } else {
            asterisk = newasterisk;
        }
        if (asterisk.equals(word)) {
            playing = false;
            return "Верно! Победа! Было загадано слово " + word +"\n" +
                            "/hangman - начать новую игру или \n" +
                    "/start - выбор игры";
        } else if (count < 7 && asterisk.contains("*")){
            return "Верно! Попробуй угадать ещё букву в слове \n" + asterisk;
        } else {
            return "Что-то пошло не так, попробуй снова";
        }
    }
    private String drawHangman() {
        if (count == 1) {
            return "Не верно, попробуй снова \n<code> \n\n\n\n" + "______</code>\n" +
                    "Осталось 6 попыток" + "\nПопробуй угадать букву в слове \n" + asterisk;
        }
        if (count == 2) {
           return "Не верно, попробуй снова\n<code>" + "|\n" + "|\n" + "|\n" + "|\n" + "______</code>\n" +
                   "Осталось 5 попыток" + "\nПопробуй угадать букву в слове \n" + asterisk;
        }
        if (count == 3) {
            return "Не верно, попробуй снова\n<code>" + "---|\n" + "|\n" + "|\n" + "|\n" + "______</code>\n" +
                    "Осталось 4 попытки" + "\nПопробуй угадать букву в слове \n" + asterisk;
        }
        if (count == 4) {
            return "Не верно, попробуй снова\n<code>" + "---|\n" + "| ( )\n" + "|\n" + "|\n" + "______</code>\n" +
                    "Осталось 3 попытки" + "\nПопробуй угадать букву в слове \n" + asterisk;
        }
        if (count == 5) {
            return "Не верно, попробуй снова\n<code>" + "---|\n" + "| ( )\n" + "|  |\n" + "|\n" + "______</code>\n" +
                    "Осталось 2 попытки" + "\nПопробуй угадать букву в слове \n" + asterisk;
        }
        if (count == 6) {
            return "Не верно, попробуй снова\n<code>" + "---|\n" + "| ( )\n" + "| /|\\\n" + "|\n" + "______</code>\n" +
                    "Осталась 1 попытка" + "\nПопробуй угадать букву в слове \n" + asterisk;
        }
        if (count == 7) {
            playing = false;
            return "Игра окончена\n<code>" + "---|\n" + "| ( )\n" + "| /|\\\n" + "| / \\\n" + "______\n</code>" +
                            "Было загадано слово: " + word +"\n" +
                            "/hangman - начать новую игру или\n" +
                    "/start - выбор игры";
        } else {
            return "Что-то пошло не так, попробуй снова";
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public void stopGame() {
        playing = false;
    }

    public String rules() {

        if (playing) {
            return RULES + "\n\nПопробуй угадать букву в слове \n" + asterisk;
        }
        return RULES;
    }
}
