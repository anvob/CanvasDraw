package com.anvob.canvasdraw.filters.action;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.anvob.canvasdraw.common.ActionFilter;

/**
 * Created by anvob on 25.02.2017.
 */

public class RoundFilter extends ActionFilter {

    private PorterDuffXfermode mode;

    public RoundFilter(int framesCount, int variant) {
        super(framesCount, variant);
        mode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void paintFrame(Canvas canvas, int curFrame) {
        if (curFrame < framesCount) {
            //save current layer
            canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), paint, Canvas.ALL_SAVE_FLAG);
            //draw transition
            int diag = (int) Math.sqrt(Math.pow(bitmap.getWidth(), 2) + Math.pow(bitmap.getHeight(), 2)) / 2;
            int stepDiag = diag / framesCount * curFrame;

            canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, diag - stepDiag, paint);

            paint.setXfermode(mode);
            //do next filter if exist
            if (mNextFilter != null) {
                mNextFilter.setBitmap(bitmap);
                mNextFilter.setPaint(paint);
                mNextFilter.paintFrame(canvas, curFrame + mNextFilter.getFramesCount() / 2);
            } else {
                canvas.drawBitmap(bitmap, 0, 0, paint);
            }
            //update current layer
            canvas.restore();
            paint.setXfermode(null);
        } else {
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }
    }
}
