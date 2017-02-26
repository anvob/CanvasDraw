package com.anvob.canvasdraw.filters.action;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.widget.RelativeLayout;

import com.anvob.canvasdraw.common.ActionFilter;

/**
 * Created by anvob on 25.02.2017.
 */

public class CurtainFilter extends ActionFilter {

    public static final int LEFT_TO_RIGHT = 0;
    public static final int RIGHT_TO_LEFT = 1;
    public static final int TOP_TO_DOWN = 2;
    public static final int DOWN_TO_TOP = 3;

    private PorterDuffXfermode mode;

    public CurtainFilter(int framesCount, int variant) {
        super(framesCount, variant);
        mode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
    }

    @Override
    public void paintFrame(Canvas canvas, int curFrame) {
        if(curFrame < framesCount) {
            int partWidth = bitmap.getWidth() / 7;
            int partHeight = bitmap.getHeight() / 7;
            int stepHeight = partHeight / framesCount * curFrame;
            int stepWidth = partWidth / framesCount * curFrame;

            int back_layer = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), paint, Canvas.ALL_SAVE_FLAG);
            int start = canvas.save();
            for (int i = 0; i < 7; i++) {
                if(mVariant == LEFT_TO_RIGHT) {
                    canvas.drawRect(0, 0, stepHeight, bitmap.getHeight(), paint);
                    canvas.translate(partWidth, 0);
                } else if(mVariant == RIGHT_TO_LEFT) {
                    if (i == 0) {
                        canvas.translate(partWidth - stepWidth, 0);
                    }
                    canvas.drawRect(0, 0, stepWidth, bitmap.getHeight(), paint);
                    canvas.translate(partWidth, 0);

                } else if(mVariant == TOP_TO_DOWN) {
                    canvas.drawRect(0, 0, bitmap.getWidth(), stepHeight, paint);
                    canvas.translate(0, partHeight);
                } else if(mVariant == DOWN_TO_TOP) {
                    if (i == 0) {
                        canvas.translate(0,partHeight - stepHeight);
                    }
                    canvas.drawRect(0, 0, bitmap.getWidth(), stepHeight, paint);
                    canvas.translate(0, partHeight);
                }
            }
            canvas.restoreToCount(start);
            paint.setXfermode(mode);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            canvas.restoreToCount(back_layer);
            paint.setXfermode(null);

        } else {
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }
    }
}
