package com.anvob.canvasdraw.filters.transition;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.anvob.canvasdraw.common.TransitionFilter;
import com.anvob.canvasdraw.filters.action.SlideInFilter;
import com.anvob.canvasdraw.filters.action.SlideOutFilter;

/**
 * Created by anvob on 17.02.2017.
 */

public class SlideInOutFilter extends TransitionFilter {

    @Override
    public void paintNext(Canvas canvas, Bitmap bitmap_old, Bitmap bitmap_new, int curFrame) {

        if (showFilter != null) {
            showFilter.setBitmap(bitmap_new);
            showFilter.paintFrame(canvas, curFrame);
        } else {
            canvas.drawBitmap(bitmap_new, 0, 0, null);
        }
        if (hideFilter != null) {
            hideFilter.setBitmap(bitmap_old);
            hideFilter.paintFrame(canvas, curFrame);
        }
    }

    public static SlideInOutFilter getSlideInOutFilter(int variant) {
        SlideInOutFilter filter = new SlideInOutFilter();
        filter.setShowFilter(new SlideInFilter(20, variant));
        filter.setHideFilter(new SlideOutFilter(20, variant));
        return filter;
    }

    public static SlideInOutFilter getSlideOutFilter(int variant) {
        SlideInOutFilter filter = new SlideInOutFilter();
        //filter.setShowFilter(new SlideInFilter(20, variant));
        filter.setHideFilter(new SlideOutFilter(20, variant));
        return filter;
    }
}
