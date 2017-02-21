package com.anvob.canvasdraw.Filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.anvob.canvasdraw.common.ActionFilter;

/**
 * Created by anvob on 17.02.2017.
 */

public class SlideOutFilter extends ActionFilter {

    public static int LEFT_TO_RIGHT = 0;
    public static int RIGHT_TO_LEFT = 1;
    public static int TOP_TO_DOWN = 2;
    public static int DOWN_TO_TOP = 3;

    private Bitmap bitmap; // битмап который отрисовается фильтром.
    private int framesCount; // количество кадров, которое создает данный фильтр, от
    private int mVariant;
    private Paint paint;
    private ActionFilter mNextFilter;
    private Matrix m;

    public SlideOutFilter(int framesCount, int variant){
        this.framesCount=framesCount;
        this.mVariant=variant;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        m = new Matrix();
    }

    @Override
    public void paintFrame(Canvas canvas, int curFrame) {
        if(curFrame<=framesCount&&curFrame>0) {
            int stepHeight = bitmap.getHeight() / framesCount * curFrame;
            int stepWidth = bitmap.getWidth() / framesCount * curFrame;
            m.reset();
            switch (mVariant) {
                case 0: //left to right
                    m.setTranslate(stepWidth, 0);
                    break;
                case 1: //right yo left
                    m.setTranslate(-stepWidth, 0);
                    break;
                case 2:
                    m.setTranslate(0, stepHeight);
                    break;
                case 3:
                    m.setTranslate(0, -stepHeight);
                    break;
            }
            canvas.drawBitmap(bitmap, m, paint);
        }
    }

    @Override
    public void setNextFilter(ActionFilter filter) {
        this.mNextFilter = filter;
    }

    @Override
    public ActionFilter getNextFilter() {
        return mNextFilter;
    }

    @Override
    public int getFramesCount() {
        return framesCount;
    }

    @Override
    public void setFramesCount(int count) {
        this.framesCount=count;
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public void setPaint(Paint paint) {

    }
}
