package com.anvob.canvasdraw.common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by anvob on 17.02.2017.
 */

// Canvas, Bitmap - стандартные классы Android.
// Canvas должен иметь квадратную форму по ширине экрана. в портретном режиме.
// Нижеуказанные классы допустимо расширять кодом по необходимости
public abstract class TransitionFilter {  // базовый класс для Transition Filters.
    private Canvas canvas; // canvas, на котором производится всё рисование
    protected int framesCount; // количество кадров, которое создает данный фильтр, от начала до конца.
    private int curFrame; // текущий номер кадра.
    protected ActionFilter hideFilter; // фильтр скрытия
    protected ActionFilter showFilter; // фильтр показа

    public void setFramesCount(int count) {
        if (showFilter != null) {
            showFilter.setFramesCount(count);
        }
        if (hideFilter != null) {
            hideFilter.setFramesCount(count);
        }
    }

    public int getFramesCount() {
        int count = 0;
        if (hideFilter != null) {
            if (count < hideFilter.getFramesCount()) {
                count = hideFilter.getFramesCount();
            }
        }
        if (showFilter != null) {
            if (count < showFilter.getFramesCount()) {
                count = showFilter.getFramesCount();
            }
        }
        return count;
    }

    public ActionFilter getHideFilter() {
        return hideFilter;
    }

    public void setHideFilter(ActionFilter hideFilter) {
        this.hideFilter = hideFilter;
    }

    public ActionFilter getShowFilter() {
        return showFilter;
    }

    public void setShowFilter(ActionFilter showFilter) {
        this.showFilter = showFilter;
    }

    public abstract void paintNext(Canvas canvas, Bitmap bitmap_old, Bitmap bitmap_new, int curFrame); // отрисовывает следующий кадр.
}

