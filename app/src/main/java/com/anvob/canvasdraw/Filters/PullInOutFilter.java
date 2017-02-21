package com.anvob.canvasdraw.Filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.anvob.canvasdraw.common.ActionFilter;
import com.anvob.canvasdraw.common.TransitionFilter;

/**
 * Created by anvob on 18.02.2017.
 */

public class PullInOutFilter extends TransitionFilter {
    private Canvas canvas; // canvas, на котором производится всё рисование
    private int framesCount; // количество кадров, которое создает данный фильтр, отначала до конца.
    private int curFrame; // текущий номер кадра.
    Paint mPaint;
    public PullInOutFilter(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private ActionFilter showFilter; // фильтр показа
    @Override
    public void setFramesCount(int count) {
        if(showFilter!=null) {
            showFilter.setFramesCount(count);
        }
    }

    @Override
    public int getFramesCount() {
        int count = 0;
        if(showFilter!=null){
            if(count<showFilter.getFramesCount()) {
                count = showFilter.getFramesCount();
            }
        }
        return  count;
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
                showFilter.setBitmap(bitmap_new);
            }
            showFilter.paintFrame(canvas,curFrame);
        }
    }

    public ActionFilter getShowFilter() {
        return showFilter;
    }

    public void setShowFilter(ActionFilter showFilter) {
        this.showFilter = showFilter;
    }
}
