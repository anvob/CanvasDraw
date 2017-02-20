package com.anvob.canvasdraw.Filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.anvob.canvasdraw.ActionFilter;
import com.anvob.canvasdraw.TransitionFilter;

/**
 * Created by anvob on 17.02.2017.
 */

public class SlideInOutFilter extends TransitionFilter {
    private Canvas canvas; // canvas, на котором производится всё рисование
    private int framesCount; // количество кадров, которое создает данный фильтр, от
    // начала до конца.
    private int curFrame; // текущий номер кадра.


    private ActionFilter hideFilter; // фильтр скрытия
    private ActionFilter showFilter; // фильтр показа

    public SlideInOutFilter(){

    }

    @Override
    public void paintNext(Canvas canvas, Bitmap bitmap_old, Bitmap bitmap_new, int curFrame) {
        if(showFilter!=null){
            //bitmap_new = Bitmap.createScaledBitmap(bitmap_new, canvas.getWidth(), canvas.getHeight(), true);
            showFilter.setBitmap(bitmap_new);
            showFilter.paintFrame(canvas,curFrame);
        }
        if(hideFilter!=null){
            //bitmap_old = Bitmap.createScaledBitmap(bitmap_old, canvas.getWidth(), canvas.getHeight(), true);
            hideFilter.setBitmap(bitmap_old);
            hideFilter.paintFrame(canvas,curFrame);
        }
    }

    @Override
    public void setFramesCount(int count) {
        if(showFilter!=null) {
            showFilter.setFramesCount(count);
        }
        if(hideFilter!=null) {
            hideFilter.setFramesCount(count);
        }
    }

    @Override
    public int getFramesCount(){
        int count = 0;
        if(hideFilter!=null){
            if(count<hideFilter.getFramesCount()) {
                count = hideFilter.getFramesCount();
            }
        }
        if(showFilter!=null){
            if(count<showFilter.getFramesCount()) {
                count = showFilter.getFramesCount();
            }
        }
        return count;
    }

    public ActionFilter getHideFilter() {
        return hideFilter;
    }

    public void setHideFilter(ActionFilter hideFilter) {
        this.hideFilter = hideFilter;
    }

    public ActionFilter getShowFilter() {
        return showFilter;
    }

    public void setShowFilter(ActionFilter showFilter) {
        this.showFilter = showFilter;
    }
}
