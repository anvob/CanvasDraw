package com.anvob.canvasdraw.filters.action;

import android.graphics.Canvas;
import android.graphics.Color;

import com.anvob.canvasdraw.common.ActionFilter;

/**
 * Created by anvob on 26.02.2017.
 */

public class FadeInFilter extends ActionFilter {
    public FadeInFilter(int framesCount, int variant) {
        super(framesCount, variant);
    }

    @Override
    public void paintFrame(Canvas canvas, int curFrame) {
        if (curFrame < framesCount) {
            int currentAlpha = 255 - 255 / framesCount * curFrame;
            if (currentAlpha > 0) {
                paint.setAlpha(currentAlpha);
            } else {
                paint.setAlpha(0);
            }
            canvas.drawBitmap(bitmap, 0, 0, paint);

        } else {
            paint.setAlpha(0);
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }
    }
}
