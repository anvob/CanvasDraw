package com.anvob.canvasdraw.filters.action;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.anvob.canvasdraw.common.ActionFilter;

/**
 * Created by anvob on 17.02.2017.
 */

public class PullInFilter extends ActionFilter {
    public static int LEFT_AND_RIGHT = 0;
    public static int TOP_AND_DOWN = 1;
    public static int TOP_LEFT_AND_BOTTOM_RIGHT = 3;
    public static int TOP_RIGHT_AND_BOTTOM_LEFT = 2;

    private PorterDuffXfermode mode;

    public PullInFilter(int framesCount, int variant) {
        super(framesCount, variant);
        mode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
    }

    @Override
    public void paintFrame(Canvas canvas, int curFrame) {
        if (curFrame <= framesCount) {
            if (curFrame < framesCount) {
                //save current layer
                canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), paint, Canvas.ALL_SAVE_FLAG);
                //draw transition by variants
                if (mVariant == LEFT_AND_RIGHT) {
                    int stepWidth = bitmap.getWidth() / framesCount / 2 * curFrame;
                    canvas.drawRect(stepWidth, 0, bitmap.getWidth() - stepWidth, bitmap.getHeight(), paint);
                } else if (mVariant == TOP_AND_DOWN) {
                    int stepHeight = bitmap.getHeight() / framesCount / 2 * curFrame;
                    canvas.drawRect(0, stepHeight, bitmap.getWidth(), bitmap.getHeight() - stepHeight, paint);
                } else if (mVariant == TOP_RIGHT_AND_BOTTOM_LEFT) {
                    int diag = (int) Math.sqrt(Math.pow(bitmap.getWidth(), 2) + Math.pow(bitmap.getHeight(), 2));
                    int stepDiag = diag / framesCount / 2 * curFrame;
                    canvas.rotate(-45, canvas.getWidth() / 2, canvas.getHeight() / 2);
                    canvas.drawRect(
                            stepDiag - (diag - bitmap.getHeight()) / 2,
                            0 - (diag - bitmap.getHeight()) / 2,
                            bitmap.getWidth() + (diag - bitmap.getHeight()) / 2 - stepDiag,
                            bitmap.getHeight() + (diag - bitmap.getHeight()) / 2, paint);
                    canvas.rotate(45, canvas.getWidth() / 2, canvas.getHeight() / 2);
                } else if (mVariant == TOP_LEFT_AND_BOTTOM_RIGHT) {
                    int diag = (int) Math.sqrt(Math.pow(bitmap.getWidth(), 2) + Math.pow(bitmap.getHeight(), 2));
                    int stepDiag = diag / framesCount / 2 * curFrame;
                    canvas.rotate(45, canvas.getWidth() / 2, canvas.getHeight() / 2);
                    canvas.drawRect(
                            stepDiag - (diag - bitmap.getHeight()) / 2,
                            0 - (diag - bitmap.getHeight()) / 2,
                            bitmap.getWidth() + (diag - bitmap.getHeight()) / 2 - stepDiag,
                            bitmap.getHeight() + (diag - bitmap.getHeight()) / 2, paint);
                    canvas.rotate(-45, canvas.getWidth() / 2, canvas.getHeight() / 2);
                }
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
        } else {
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }
    }
}
