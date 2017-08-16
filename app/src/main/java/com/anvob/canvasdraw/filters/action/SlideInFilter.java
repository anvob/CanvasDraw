package com.anvob.canvasdraw.filters.action;

import android.graphics.Canvas;
import android.graphics.Matrix;

import com.anvob.canvasdraw.common.ActionFilter;

/**
 * Created by anvob on 17.02.2017.
 */

public class SlideInFilter extends ActionFilter {

    public static final int LEFT_TO_RIGHT = 0;
    public static final int RIGHT_TO_LEFT = 1;
    public static final int TOP_TO_DOWN = 2;
    public static final int DOWN_TO_TOP = 3;

    private Matrix m = new Matrix();

    public SlideInFilter(int framesCount, int variant) {
        super(framesCount, variant);

    }

    @Override
    public void paintFrame(Canvas canvas, int curFrame) {

        if (curFrame < framesCount) {
            float stepHeight = (float) bitmap.getHeight() / framesCount * curFrame;
            float stepWidth = (float) bitmap.getWidth() / framesCount * curFrame;
            switch (mVariant) {
                case LEFT_TO_RIGHT: //left to right
                    if (paint.getXfermode() != null) {
                        canvas.drawRect(stepWidth, 0, bitmap.getWidth(), bitmap.getHeight(), paint);
                    }
                    m.setTranslate(stepWidth - bitmap.getWidth(), 0);
                    break;
                case RIGHT_TO_LEFT: // right to left
                    if (paint.getXfermode() != null) {
                        canvas.drawRect(0, 0, bitmap.getWidth() - stepWidth, bitmap.getHeight(), paint);
                    }
                    m.setTranslate(bitmap.getWidth() - stepWidth, 0);
                    break;
                case TOP_TO_DOWN: // top to down
                    if (paint.getXfermode() != null) {
                        canvas.drawRect(0, stepHeight, bitmap.getWidth(), bitmap.getHeight(), paint);
                    }
                    m.setTranslate(0, stepHeight - bitmap.getHeight());
                    break;
                case DOWN_TO_TOP: // down to top
                    if (paint.getXfermode() != null) {
                        canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight() - stepHeight, paint);
                    }
                    m.setTranslate(0, bitmap.getHeight() - stepHeight);
                    break;
            }
            canvas.drawBitmap(bitmap, m, paint);

            m.reset();
        } else {
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }
    }
}
