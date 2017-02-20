package com.anvob.canvasdraw.Filters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.anvob.canvasdraw.ActionFilter;

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

    public SlideInFilter( int framesCount, int variant){
        this.framesCount=framesCount;
        this.mVariant=variant;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setVariant(int variant){
        mVariant=variant;
    }

    @Override
    public Bitmap paintFrame(Canvas canvas, int curFrame) {
        int width=0,height=0,x=0,y=0,drawX=0,drawY=0;
        if(curFrame<=framesCount&&curFrame>0) {
            if(curFrame<framesCount) {
                int stepHeight = bitmap.getHeight() / framesCount * curFrame;
                int stepWidth = bitmap.getWidth() / framesCount * curFrame;
                switch (mVariant){
                    case 0:
                        width = stepWidth;
                        height = bitmap.getHeight();
                        x = bitmap.getWidth() - width;
                        y = 0;
                        drawX=0;
                        drawY=0;
                        break;
                    case 1:
                        width = stepWidth;
                        height = bitmap.getHeight();
                        x = 0;
                        y = 0;
                        drawX=canvas.getWidth() - width;
                        drawY=0;
                        break;
                    case 2:
                        x = 0;
                        y = 0;
                        width = bitmap.getWidth();
                        height = stepHeight;
                        drawY = stepHeight;
                        drawX=0;
                        break;
                    case 3:
                        x = 0;
                        y = bitmap.getWidth()-stepHeight;
                        width = bitmap.getWidth();
                        height = stepHeight;
                        drawX=0;
                        drawY=0;
                        break;
                }
                Bitmap b2 = Bitmap.createBitmap(bitmap, x, y, width, height);
                canvas.drawBitmap(b2,drawX,drawY,paint);
                return b2;
            } else{
                canvas.drawBitmap(bitmap,0,0,paint);
            }

        }else{
            canvas.drawBitmap(bitmap,0,0,paint);
        }
        //if(mNextFilter!=null){
        //    mNextFilter.setBitmap(bitmap);
        //    mNextFilter.paintFrame(canvas,curFrame);
        //}
        return bitmap;
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
