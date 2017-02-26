package com.anvob.canvasdraw.filters.transition;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.anvob.canvasdraw.common.ActionFilter;
import com.anvob.canvasdraw.common.TransitionFilter;
import com.anvob.canvasdraw.filters.action.PullInFilter;
import com.anvob.canvasdraw.filters.action.PullOutFilter;
import com.anvob.canvasdraw.filters.action.SlideInFilter;

import java.security.PublicKey;

/**
 * Created by anvob on 18.02.2017.
 */

public class PullInOutFilter extends TransitionFilter {

    Paint mPaint;

    public PullInOutFilter(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void paintNext(Canvas canvas, Bitmap bitmap_old, Bitmap bitmap_new, int curFrame) {
        if(showFilter!=null){
            if(showFilter.getClass()==PullOutFilter.class){
                if(showFilter.getNextFilter()!=null) {
                    ActionFilter f = showFilter.getNextFilter();
                    f.setBitmap(bitmap_new);
                    canvas.drawColor(Color.BLACK);
                    f.paintFrame(canvas, curFrame + f.getFramesCount()/2);
                } else {
                    canvas.drawBitmap(bitmap_new, 0, 0, mPaint);
                }
                showFilter.setBitmap(bitmap_old);
            } else {
                canvas.drawBitmap(bitmap_old,0,0,mPaint);
                showFilter.setBitmap(bitmap_new);
            }
            showFilter.paintFrame(canvas,curFrame);
        }
    }

    public static PullInOutFilter getPullInOutFilter(int pull_variant, int slidein_variant){
        PullInOutFilter filter = new PullInOutFilter();
        if(pull_variant >= 0 && pull_variant < 4) {
            PullOutFilter pof = new PullOutFilter(20, pull_variant);
            pof.setNextFilter(new SlideInFilter(20, slidein_variant));
            filter.setShowFilter(pof);
        } else if( pull_variant < 8){
            PullInFilter pif = new PullInFilter(20, pull_variant-4);
            pif.setNextFilter(new SlideInFilter(20, slidein_variant));
            filter.setShowFilter(pif);
        }
        return filter;
    }
}
