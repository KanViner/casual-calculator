package ru.kanviner.casualcalculator.whatchers;

import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Created by Zhenya on 03.02.2017.
 * Класс нужен чтобы хранить текущее состояние дисплея, а также взаимодействовать с ним.
 */

public class Calculator {

    private TextView displayView;
    private State state;
    private Operation operation;

    private int maxLength; //Максимальная длинна вводимых чисел
    private String firstNumber = "";
    private boolean is1Dot;
    private String secondNumber = "";
    private boolean is2Dot;

    private Character[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static Character DOT = '.';

    public Calculator(TextView displayView) {
        this.displayView = displayView;
        state = State.INPUT_FIRST;
        operation = Operation.PLUS;
        is1Dot = false;
        is2Dot = false;
        maxLength = 16;
    }

    // Метод обнуляет класс так, как будто он только создан
    private void nulling () {
        displayView.setText("");
        is1Dot = false;
        is2Dot = false;
        firstNumber = "";
        secondNumber = "";
        state = State.INPUT_FIRST;
    }

    /**
     * Метод добавляет digit на дисплей, с валидацией
     * @param digit
     */
    public void append (Character digit) {

        if (!checkContains(digit, digits, 0) && digit != DOT) return;

        int len1 = len1();
        int len2 = len2();

        if (state == State.INPUT_FIRST) {
            if ( digit == DOT && !is1Dot && len1 > 0 && len1 <= maxLength - 1 ) {
                firstNumber = firstNumber + digit;
                app(digit.toString());
                is1Dot = true;
            }
            if ( checkContains(digit, digits, 0) && len1 < maxLength ) {
                firstNumber = firstNumber + digit;
                app(digit.toString());
            }
        }
        if (state == State.INPUT_SECOND) {
            if ( digit == DOT && !is2Dot && len2 > 0 && len2 <= maxLength - 1 ) {
                secondNumber = secondNumber + digit;
                app(digit.toString());
                is2Dot = true;
            }
            if ( checkContains(digit, digits, 0) && len2 < maxLength ) {
                secondNumber = secondNumber + digit;
                app(digit.toString());
            }
        }
        if (state == State.END) {
            nulling();
            append(digit);
        }
    }

    // Добавляет к тексту вьюшки appStr
    private void app (String appStr) {
        displayView.setText(displayView.getText() + appStr.toString());
    }

    //
    // ОПЕРАЦИИ СЛОЖЕНИЯ, ВЫЧИТАНИЯ, УМНОЖЕНИЯ, ДЕЛЕНИЯ
    //

    public void plus () {
        if (state == State.END || state == State.INPUT_SECOND || len1() == 0) return;
        if (firstNumber.length() == 1 && firstNumber.equals("-")) return; //проверка чтобы не был введен только минус

        operation = Operation.PLUS;
        state = State.INPUT_SECOND;
        app("\n+\n");
    }

    public void minus () {
        if ( state == State.END || (state == State.INPUT_SECOND && secondNumber.length() > 0) ) return;
        if (firstNumber.length() == 1 && firstNumber.equals("-")) return; //проверка чтобы не был введен только минус

        if (state == State.INPUT_FIRST && firstNumber.length() == 0) {
            firstNumber = "-";
            app("-");
            return;
        }
        if (state == State.INPUT_SECOND) {
            secondNumber = "-";
            app("-");
            return;
        }

        operation = Operation.MINUS;
        state = State.INPUT_SECOND;
        app("\n-\n");
    }

    public void mult () {
        if (state == State.END || state == State.INPUT_SECOND || len1() == 0) return;
        if (firstNumber.length() == 1 && firstNumber.equals("-")) return; //проверка чтобы не был введен только минус

        operation = Operation.MULT;
        state = State.INPUT_SECOND;
        app("\n×\n");
    }

    public void div () {
        if (state == State.END || state == State.INPUT_SECOND || len1() == 0) return;
        if (firstNumber.length() == 1 && firstNumber.equals("-")) return; //проверка чтобы не был введен только минус

        operation = Operation.DIV;
        state = State.INPUT_SECOND;
        app("\n/\n");
    }

    //
    // ОПЕРАЦИИ ВЗЯТИЯ КОРНЯ, УДАЛЕНИЙ И РАВЕНСТВА
    //

    public void sqrt () {
        if (state == State.END || state == State.INPUT_SECOND || len1() == 0) return;
        if (firstNumber.length() == 1 && firstNumber.equals("-")) return; //проверка чтобы не был введен только минус

        double result = Math.sqrt(Double.parseDouble(firstNumber));
        nulling();
        firstNumber = result + "";
        app(result + "");
        setState(State.INPUT_FIRST);
    }

    public void deleteOne () {
        if (state == State.END) return;

        if (state == State.INPUT_FIRST && firstNumber.length() > 0) {
            firstNumber = displayView.getText().subSequence(0, displayView.length()-1).toString();
            displayView.setText(firstNumber);
            return;
        }

        if (state == State.INPUT_SECOND) {
            if (secondNumber.length() > 0) {
                secondNumber = displayView.getText()
                        .subSequence(displayView.length()-secondNumber.length(), displayView.length()-1).toString();
                displayView.setText(displayView.getText().subSequence(0, displayView.length() - 1));
                return;
            }
            if (secondNumber.length() == 0) {
                displayView.setText(displayView.getText().subSequence(0, displayView.length() - 3));
                setState(State.INPUT_FIRST);
                return;
            }
        }
    }

    public void deleteAll () {
        nulling();
    }

    public void equality () {
        if (state == State.INPUT_FIRST || state == State.END || len2() == 0) return;
        if (secondNumber.length() == 1 && secondNumber.equals("-")) return; //проверка чтобы не был введен только минус

        double num1 = Double.parseDouble(firstNumber);
        double num2 = Double.parseDouble(secondNumber);

        double result = 0;

        switch (operation) {
            case PLUS:
                result = num1 + num2;
                break;
            case MINUS:
                result = num1 - num2;
                break;
            case MULT:
                result = num1 * num2;
                break;
            case DIV:
                result = num1 / num2;
                break;
        }

        nulling();
        firstNumber = result + "";

        if (result % 1 == 0) app( (int) result + "" );
        else app(result + "");

        setState(State.INPUT_FIRST);
    }

    // Рекурсивный метод проверяет есть ли в массиве value
    private boolean checkContains ( Character value, Character[] array, int index ) {

        try {
            return value.equals(array[index]) || checkContains(value, array, ++index);

        } catch ( IndexOutOfBoundsException e ) {
            return false;
        }
    }

    //
    // Нижние методы возвращают длинну чисел за вычетом точки
    //

    private int len1 () {
        return is1Dot ? firstNumber.length() - 1 : firstNumber.length();
    }
    private int len2() {
        return is2Dot ? secondNumber.length() - 1 : secondNumber.length();
    }

    //
    // ГЕТТЕРЫ И СЕТТЕРЫ
    //

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    //
    // ENUM-ы
    //

    public enum State {INPUT_FIRST, INPUT_SECOND, END}
    public enum Operation {PLUS, MINUS, MULT, DIV}
}
