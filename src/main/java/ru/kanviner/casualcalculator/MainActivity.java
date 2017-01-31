package ru.kanviner.casualcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import ru.kanviner.casualcalculator.customviews.CalcButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        setButtonsSizes();

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
}
