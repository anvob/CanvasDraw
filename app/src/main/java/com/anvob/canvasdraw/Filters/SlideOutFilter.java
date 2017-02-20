package com.anvob.canvasdraw.Filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.anvob.canvasdraw.ActionFilter;

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

    public SlideOutFilter(int framesCount, int variant){
        this.framesCount=framesCount;
        this.mVariant=variant;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public Bitmap paintFrame(Canvas canvas, int curFrame) {
        int width=0;
        int height=0;
        int x=0;
        int y=0;
        int drawX=0;
        int drawY=0;
        if(curFrame<=framesCount&&curFrame>0) {
            int stepHeight = bitmap.getHeight() / framesCount * curFrame;
            int stepWidth = bitmap.getWidth() / framesCount * curFrame;
                switch (mVariant){
                    case 0: //left to right
                        x = 0;
                        y = 0;
                        width = bitmap.getWidth() - bitmap.getWidth() / framesCount * curFrame + 1;
                        height = bitmap.getHeight();
                        drawX=bitmap.getWidth() / framesCount * curFrame + 1;
                        drawY=0;
                        break;
                    case 1: //right yo left
                        x = bitmap.getWidth() / framesCount * curFrame + 1;
                        y = 0;
                        width = bitmap.getWidth() - x;
                        height = bitmap.getHeight();
                        drawX=0;
                        drawY=0;
                        break;
                    case 2:
                        x = 0;
                        y = 0;
                        width= bitmap.getWidth();
                        height = bitmap.getHeight()-stepHeight;
                        drawX = 0;
                        drawY = stepHeight;
                        break;
                    case 3:
                        drawX = 0;
                        drawY = 0;
                        width = bitmap.getWidth();
                        height = bitmap.getHeight()-stepHeight;
                        x= 0;
                        y = stepHeight;
                        break;
                }
                Bitmap b2 = Bitmap.createBitmap(bitmap, x, y, width, height);
                canvas.drawBitmap(b2,drawX,drawY,paint);
                b2.recycle();
        }
        return bitmap;
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
}
