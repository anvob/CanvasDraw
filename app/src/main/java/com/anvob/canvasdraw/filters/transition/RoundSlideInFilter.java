package com.anvob.canvasdraw.filters.transition;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.anvob.canvasdraw.common.TransitionFilter;
import com.anvob.canvasdraw.filters.action.RoundFilter;
import com.anvob.canvasdraw.filters.action.SlideInFilter;

/**
 * Created by anvob on 25.02.2017.
 */

public class RoundSlideInFilter extends TransitionFilter {

    @Override
    public void paintNext(Canvas canvas, Bitmap bitmap_old, Bitmap bitmap_new, int curFrame) {

        if (showFilter != null) {
            canvas.drawColor(Color.BLACK);
            showFilter.setBitmap(bitmap_new);
            showFilter.paintFrame(canvas, curFrame);
        }
        if (hideFilter != null) {
            hideFilter.setBitmap(bitmap_old);
            hideFilter.paintFrame(canvas, curFrame);
        }
    }

    public static RoundSlideInFilter getRoundSlideInFilter(int variant) {
        RoundSlideInFilter filter = new RoundSlideInFilter();
        filter.setShowFilter(new SlideInFilter(20, variant));
        filter.setHideFilter(new RoundFilter(20, 0));
        return filter;
    }
}
