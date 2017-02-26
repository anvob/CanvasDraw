package com.anvob.canvasdraw.common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by anvob on 17.02.2017.
 */

public abstract class ActionFilter { // базовый класс для фильтров показа и скрытия.

    protected Bitmap bitmap; // битмап который отрисовается фильтром.
    protected int framesCount; // количество кадров, которое создает данный фильтр, от начала до конца.
    protected int mVariant;
    protected ActionFilter mNextFilter;
    protected Paint paint;

    public ActionFilter(int framesCount, int variant) {
        this.framesCount = framesCount;
        this.mVariant = variant;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public abstract void paintFrame(Canvas canvas, int curFrame); // отрисовывает следующий кадр.

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setNextFilter(ActionFilter filter) {
        this.mNextFilter = filter;
    }

    public ActionFilter getNextFilter() {
        return mNextFilter;
    }

    public void setVariant(int variant) {
        this.mVariant = variant;
    }

    public int getVariant() {
        return mVariant;
    }

    public int getFramesCount() {
        return framesCount;
    }

    public void setFramesCount(int count) {
        this.framesCount = count;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}

