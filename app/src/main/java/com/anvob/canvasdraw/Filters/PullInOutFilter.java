package com.anvob.canvasdraw.Filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.anvob.canvasdraw.ActionFilter;
import com.anvob.canvasdraw.TransitionFilter;

/**
 * Created by anvob on 18.02.2017.
 */

public class PullInOutFilter extends TransitionFilter {
    private Canvas canvas; // canvas, на котором производится всё рисование
    private int framesCount; // количество кадров, которое создает данный фильтр, отначала до конца.
    private int curFrame; // текущий номер кадра.

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
            if(showFilter.getNextFilter()!=null) {
                ActionFilter f = showFilter.getNextFilter();
                Bitmap targetBitmap = Bitmap.createBitmap(bitmap_new.getWidth(), bitmap_new.getHeight(),Bitmap.Config.ARGB_4444);
                Canvas c = new Canvas(targetBitmap);
                c.drawBitmap(bitmap_new,0,0,new Paint(Paint.ANTI_ALIAS_FLAG));
                c.drawColor(Color.BLACK);
                f.setBitmap(bitmap_new);
                bitmap_new = f.paintFrame(c,curFrame+f.getFramesCount()/2);
                targetBitmap.recycle();
            }
            if(showFilter.getClass()==PullOutFilter.class){
                canvas.drawColor(Color.BLACK);
                canvas.drawBitmap(bitmap_new, 0, 0, new Paint(Paint.ANTI_ALIAS_FLAG));
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
