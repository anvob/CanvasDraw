package com.anvob.canvasdraw.filters.transition;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.anvob.canvasdraw.common.ActionFilter;
import com.anvob.canvasdraw.common.TransitionFilter;
import com.anvob.canvasdraw.filters.action.CurtainFilter;
import com.anvob.canvasdraw.filters.action.SlideInFilter;

/**
 * Created by anvob on 25.02.2017.
 */

public class CurtainSlideInFilter extends TransitionFilter {

    Paint mPaint;

    public CurtainSlideInFilter() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void paintNext(Canvas canvas, Bitmap bitmap_old, Bitmap bitmap_new, int curFrame) {
        if (showFilter != null) {
            if (showFilter.getNextFilter() != null) {
                ActionFilter f = showFilter.getNextFilter();
                f.setBitmap(bitmap_new);
                canvas.drawColor(Color.BLACK);
                f.paintFrame(canvas, curFrame + f.getFramesCount() / 2);
            } else {
                canvas.drawBitmap(bitmap_new, 0, 0, mPaint);
            }
            showFilter.setBitmap(bitmap_old);
            showFilter.paintFrame(canvas, curFrame);
        }
    }

    public static CurtainSlideInFilter getCurtainFilter(int curtain_var, int slidein_var) {
        CurtainSlideInFilter filter = new CurtainSlideInFilter();
        CurtainFilter cf = new CurtainFilter(20, curtain_var);
        cf.setNextFilter(new SlideInFilter(20, slidein_var));
        filter.setShowFilter(cf);
        return filter;
    }
}
