package org.hangman;

public class BullsCows {
    private int[] numberArray = new int[4];
    private boolean isPlaying = false;
    private int count;
    private int[] guess = new int[4];

    private String number = "";
    private final String RULES = "<b>Правила игры:</b>\n" +
            "Загадано 4-значное число с неповторяющимися цифрами. Вам нужно сделать попытку отгадать число. Попытка " +
            "это ввод 4-значного числа с неповторяющимися цефрами. В ответ вы получите информацию, о том, сколько чисел угдано без " +
            "совпадения их позиции с позициями в загаданном числе (коровы), а также о том, сколько чисел угадано " +
            "на тех же позициях, что и в загаданном числе (быки). Например:\n" +
            "Задумано число: \"3219\"\n" +
            "Попытка: \"2310\"\n" +
            "Результат: две \"коровы\" (две цифры: \"2\" и \"3\" - угаданы на неверных позициях) и один \"бык\" (одна цифра \"1\" " +
            "угадана вплоть до позиции).";

    private void generateNumber() {
        for (int i = 0; i < 4; i++) {
            int a = (int) (Math.random() * 10);
            while (a == 0 && i == 0) {                 // просто потому что мне не нравится, что число может начинаться с 0
                a = (int) (Math.random() * 10);
            }
            while (contains(numberArray, a)) {
                a = (int) (Math.random() * 10);
            }
            numberArray[i] = a;
            number += Integer.toString(numberArray[i]);
        }
    }

    private boolean contains(int[] arr, int num) {
        for (int i : arr) {
            if (i == num) {
                return true;
            }
        }
        return false;
    }

    private boolean repeated(int[] arr, int num) {
        int count = 0;
        for (int i : arr) {
            if (num == i) {
                count += 1;
            }
        }
        if (count > 1) {
            return true;
        } else {
            return false;
        }
    }

    public String newGame() {
        isPlaying = true;
        number = "";
        generateNumber();
        count = 0;
        return "Я придумал четырехзначное число, попробуй угадать его.\n" +
                "для отображения правил игры нажми /rules\n\n" +
                "Введи число";
    }

    public String guess(String guess) {
        int cows = 0;
        int bulls = 0;
        for (int i = 0; i < 4; i++) {
            this.guess[i] = Character.getNumericValue(guess.charAt(i));
        }
        for (int i = 0; i < 4; i++) {
            if (repeated(this.guess, this.guess[i])) {
                return "Цифры не должны повторяться!";
            }
        }
        count++;
        for (int i = 0; i < 4; i++) {
            if (this.guess[i] == numberArray[i]) {
                bulls++;
            } else if (contains(numberArray, this.guess[i])) {
                cows++;
            }
        }

        if (bulls + cows > 2 && 4 > bulls && bulls > 1) {
            return "Быки - " + bulls + ", Коровы - " + cows + ". Хороший ход! \n\n" +
                    "Введи число";
        } else if (bulls == 4) {
            isPlaying = false;
            return "Победа! Было загадано число " + number +"\n" +
                    "Затрачено ходов: " + count + "\n\n" +
                    "/bullscows - начать новую игру или \n" +
                    "/start - выбор игры";
        }

        return "Быки - " + bulls + ", Коровы - " + cows +"\n\n" +
                "Введи число";
    }

    public void stopGame() {
        isPlaying = false;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public String rules() {

        if (isPlaying) {
            return RULES + "\n\nВведи число";
        }
        return RULES;
    }
}
