package com.anvob.canvasdraw.filters.transition;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.anvob.canvasdraw.common.ActionFilter;
import com.anvob.canvasdraw.common.TransitionFilter;
import com.anvob.canvasdraw.filters.action.FadeInFilter;
import com.anvob.canvasdraw.filters.action.SlideInFilter;

/**
 * Created by anvob on 26.02.2017.
 */

public class FadeInSlideFilter extends TransitionFilter {

    Paint mPaint;

    public FadeInSlideFilter() {
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

    public static FadeInSlideFilter getFadeInSlideFilter(int variant) {
        FadeInSlideFilter filter = new FadeInSlideFilter();
        FadeInFilter fif = new FadeInFilter(20, 0);
        fif.setNextFilter(new SlideInFilter(20, variant));
        filter.setShowFilter(fif);
        return filter;
    }
}
