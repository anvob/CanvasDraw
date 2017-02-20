package com.anvob.canvasdraw;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by anvob on 17.02.2017.
 */

// Canvas, Bitmap - стандартные классы Android.
// Canvas должен иметь квадратную форму по ширине экрана. в портретном режиме.
// Нижеуказанные классы допустимо расширять кодом по необходимости
public abstract class TransitionFilter  {  // базовый класс для Transition Filters.
    private Canvas canvas; // canvas, на котором производится всё рисование
    private int framesCount; // количество кадров, которое создает данный фильтр, от
    // начала до конца.
    private int curFrame; // текущий номер кадра.
    private ActionFilter hideFilter; // фильтр скрытия
    private ActionFilter showFilter; // фильтр показа

    public abstract void setFramesCount(int count);
    public abstract int getFramesCount();
    public abstract void paintNext(Canvas canvas, Bitmap bitmap_old, Bitmap bitmap_new, int curFrame); // отрисовывает следующий кадр.
}

