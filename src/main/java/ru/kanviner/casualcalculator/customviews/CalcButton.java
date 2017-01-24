package ru.kanviner.casualcalculator.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Size;
import android.widget.Button;

/**
 * Created by Zhenya on 21.01.2017.
 * Работает ТОЛЬКО с GridLayout
 * Кнопка всегда квадратная. Длина стороны равна ширине если ширана больше высоты, и высоте если иначе
 */

public class CalcButton extends Button {

    //Переменная позволяет сохранять пропорции кнопок
    private double xyRelation = 1.135593220338983;

    public CalcButton(Context context) {
        super(context);
    }

    public CalcButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalcButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CalcButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int msrHeight = getMeasuredHeight();
        final int msrWidth = getMeasuredWidth();

        Size mySize = calcSize(msrHeight, msrWidth);

        setMeasuredDimension(mySize.getWidth(), mySize.getHeight());
    }

    /**
     * Метод считает размеры опираясь на значение переменной xyRelation
     *
     * @param msrHeight
     * @param msrWidth
     * @return
     */
    private Size calcSize(int msrHeight, int msrWidth) {
        int width;
        int heigth;

        width = msrWidth;
        heigth = (int) (width / xyRelation);

        return new Size(width, heigth);
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        Size size = calcSize(w, h);
        super.onSizeChanged(size.getWidth(), size.getHeight(), oldw, oldh);
    }

    public double getXyRelation() {
        return xyRelation;
    }

    public void setXyRelation(double xyRelation) {
        this.xyRelation = xyRelation;
    }
}
