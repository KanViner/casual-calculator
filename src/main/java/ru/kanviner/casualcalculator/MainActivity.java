package ru.kanviner.casualcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import ru.kanviner.casualcalculator.customviews.CalcButton;
import ru.kanviner.casualcalculator.whatchers.Calculator;

public class MainActivity extends Activity{

    //Вьюшка на которой отображаются вводимы числа и результат
    Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
        getActionBar().hide();

        setButtonsSizes();
        calculator = new Calculator( (TextView)findViewById(R.id.textView) );

        CalcButton deleteBtn = (CalcButton) findViewById(R.id.deleteBtn);
        deleteBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                calculator.deleteAll();
                return true;
            }
        });
    }

    /**
     * Метод устанавливает размеры для кнопок
     *
     */
    private void setButtonsSizes() {
        CalcButton eqBtn = (CalcButton) findViewById(R.id.equallyBtn);
        CalcButton zerBtn = (CalcButton) findViewById(R.id.zeroBtn);

        eqBtn.setXyRelation(eqBtn.getXyRelation() / 2.0);
        zerBtn.setXyRelation(zerBtn.getXyRelation() * 2.0);
    }

    /**
     * Метод выполняет первичную обработку нажатий кнопок.
     * Потом же делегирует обработку конкретным сущностям.
     *
     * @param view кликнутая кнопка
     */
    public void buttonClicked(View view) {

        boolean isNormal = view instanceof Button;
        if (!isNormal) return;

        Button btn = (Button) view;
        switch (btn.getId()) {
            case R.id.zeroBtn:
                calculator.append('0');
                break;
            case R.id.oneBtn:
                calculator.append('1');
                break;
            case R.id.twoBtn:
                calculator.append('2');
                break;
            case R.id.threeBtn:
                calculator.append('3');
                break;
            case R.id.fourBtn:
                calculator.append('4');
                break;
            case R.id.fiveBtn:
                calculator.append('5');
                break;
            case R.id.sixBtn:
                calculator.append('6');
                break;
            case R.id.sevenBtn:
                calculator.append('7');
                break;
            case R.id.eightBtn:
                calculator.append('8');
                break;
            case R.id.nineBtn:
                calculator.append('9');
                break;

            case R.id.dotBtn:
                calculator.append(Calculator.DOT);
                break;

            case R.id.plusBtn:
                calculator.plus();
                break;
            case R.id.minusBtn:
                calculator.minus();
                break;
            case R.id.multBtn:
                calculator.mult();
                break;
            case R.id.divBtn:
                calculator.div();
                break;

            case R.id.sqrBtn:
                calculator.sqrt();
                break;
            case R.id.deleteBtn:
                calculator.deleteOne();
                break;
            case R.id.equallyBtn:
                calculator.equality();
                break;
        }
    }
}
