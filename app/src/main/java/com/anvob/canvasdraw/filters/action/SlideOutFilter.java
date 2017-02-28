package com.anvob.canvasdraw.filters.action;

import android.graphics.Canvas;
import android.graphics.Matrix;

import com.anvob.canvasdraw.common.ActionFilter;

/**
 * Created by anvob on 17.02.2017.
 */

public class SlideOutFilter extends ActionFilter {

    public static final int LEFT_TO_RIGHT = 0;
    public static final int RIGHT_TO_LEFT = 1;
    public static final int TOP_TO_DOWN = 2;
    public static final int DOWN_TO_TOP = 3;

    private Matrix m;

    public SlideOutFilter(int framesCount, int variant) {
        super(framesCount, variant);
        m = new Matrix();
    }

    @Override
    public void paintFrame(Canvas canvas, int curFrame) {
        if (curFrame < framesCount) {
            float stepHeight = (float) bitmap.getHeight() / framesCount * curFrame;
            float stepWidth = (float) bitmap.getWidth() / framesCount * curFrame;

            switch (mVariant) {
                case LEFT_TO_RIGHT: //left to right
                    m.setTranslate(stepWidth, 0);
                    break;
                case RIGHT_TO_LEFT: //right yo left
                    m.setTranslate(-stepWidth, 0);
                    break;
                case TOP_TO_DOWN: //top to down
                    m.setTranslate(0, stepHeight);
                    break;
                case DOWN_TO_TOP: //down to top
                    m.setTranslate(0, -stepHeight);
                    break;
            }
            canvas.drawBitmap(bitmap, m, paint);
            m.reset();
        } else {
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }
    }
}
