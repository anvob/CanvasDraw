package com.anvob.canvasdraw.Filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.anvob.canvasdraw.common.ActionFilter;

/**
 * Created by anvob on 17.02.2017.
 */

public class SlideInFilter extends ActionFilter {

    public static int LEFT_TO_RIGHT = 0;
    public static int RIGHT_TO_LEFT = 1;
    public static int TOP_TO_DOWN = 2;
    public static int DOWN_TO_TOP = 3;

    private Bitmap bitmap; // битмап который отрисовается фильтром.
    private int framesCount; // количество кадров, которое создает данный фильтр, от начала до конца.
    private int mVariant;
    private Paint paint;
    private ActionFilter mNextFilter;
    Matrix m = new Matrix();

    public SlideInFilter( int framesCount, int variant){
        this.framesCount=framesCount;
        this.mVariant=variant;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public void setPaint(Paint paint) {
        this.paint=paint;
    }

    public void setVariant(int variant){
        mVariant=variant;
    }

    @Override
    public void paintFrame(Canvas canvas, int curFrame) {
        if(curFrame<=framesCount&&curFrame>0) {
            if(curFrame<framesCount) {
                int stepHeight = bitmap.getHeight() / framesCount * curFrame;
                int stepWidth = bitmap.getWidth() / framesCount * curFrame;
                m.reset();
                switch (mVariant){
                    case 0: //left to right
                        if(paint.getXfermode()!=null) {
                            canvas.drawRect(stepWidth, 0, bitmap.getWidth(), bitmap.getHeight(), paint);
                        }
                        m.setTranslate(stepWidth-bitmap.getWidth(),0);
                        break;
                    case 1: // right to left
                        if(paint.getXfermode()!=null) {
                            canvas.drawRect(0, 0, bitmap.getWidth() - stepWidth, canvas.getHeight(), paint);
                        }
                        m.setTranslate(bitmap.getWidth()-stepWidth,0);
                        break;
                    case 2: // top to down
                        if(paint.getXfermode()!=null) {
                            canvas.drawRect(0, stepHeight, bitmap.getWidth(), canvas.getHeight(), paint);
                        }
                        m.setTranslate(0,stepHeight-bitmap.getHeight());
                        break;
                    case 3: // down to top
                        if(paint.getXfermode()!=null) {
                            canvas.drawRect(0, 0, bitmap.getWidth(), canvas.getHeight() - stepHeight, paint);
                        }
                        m.setTranslate(0,bitmap.getHeight()-stepHeight);
                        break;
                }
                canvas.drawBitmap(bitmap,m,paint);
            } else{
                canvas.drawBitmap(bitmap,0,0,paint);
            }
        }else{
            canvas.drawBitmap(bitmap,0,0,paint);
        }
    }

    @Override
    public void setNextFilter(ActionFilter ﬁlter) {
        this.mNextFilter=ﬁlter;
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
}
