package com.anvob.canvasdraw;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by anvob on 17.02.2017.
 */

public abstract class ActionFilter { // базовый класс для фильтров показа и скрытия.
    private Bitmap bitmap; // битмап который отрисовается фильтром.
    private int framesCount; // количество кадров, которое создает данный фильтр, от
    // начала до конца.
    public abstract Bitmap paintFrame(Canvas canvas, int curFrame); // отрисовывает следующий кадр.
    public abstract void setNextFilter(ActionFilter ﬁlter); // устанавливает следующий фильтр для
    //  составного фильтра.
    public abstract ActionFilter getNextFilter(); // получает следующий фильтр для составного
    // фильтра.
    public abstract int getFramesCount();
    public abstract void setFramesCount(int count);
    public abstract void setBitmap(Bitmap bitmap);

}

