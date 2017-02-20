package com.anvob.canvasdraw.Filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region;

import com.anvob.canvasdraw.ActionFilter;

/**
 * Created by anvob on 17.02.2017.
 */

public class PullInFilter extends ActionFilter {
    public static int LEFT_AND_RIGHT = 0;
    public static int TOP_AND_DOWN = 1;
    public static int TOP_LEFT_AND_BOTTOM_RIGHT = 2;
    public static int TOP_RIGHT_AND_BOTTOM_LEFT = 3;

    private Bitmap bitmap; // битмап который отрисовается фильтром.
    private int framesCount; // количество кадров, которое создает данный фильтр, от начала до конца.
    private int mVariant;
    private Paint paint;
    private ActionFilter mNextFilter;

    public PullInFilter(int framesCount, int variant){
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
        int width=0,height=0,x=0,y=0;
        if(curFrame<=framesCount&&curFrame>0) {
            if(curFrame<framesCount) {
                int stepHeight = bitmap.getHeight() / framesCount/2 * curFrame;
                int stepWidth = bitmap.getWidth() / framesCount/2 * curFrame;
                if(mVariant==0) {
                    x = bitmap.getWidth()/2-stepWidth;
                    y = 0;
                    width = stepWidth;
                    height = bitmap.getHeight();
                    Bitmap b1 = Bitmap.createBitmap(bitmap, x, y, width, height);
                    x = bitmap.getWidth()/2;
                    y = 0;
                    width=stepWidth;
                    height = bitmap.getHeight();
                    Bitmap b2 = Bitmap.createBitmap(bitmap, x, y, width, height);
                    canvas.drawBitmap(b1, 0, 0, paint);
                    b1 = null;
                    canvas.drawBitmap(b2, canvas.getWidth()-stepWidth, 0, paint);
                    b2 = null;
                } else if(mVariant==1){
                    x = 0;
                    y = bitmap.getHeight()/2-stepHeight;
                    width = bitmap.getWidth();
                    height = stepHeight;
                    Bitmap b1 = Bitmap.createBitmap(bitmap, x, y, width, height);
                    x = 0;
                    y = bitmap.getHeight()/2;
                    width = bitmap.getWidth();
                    height = stepHeight;
                    Bitmap b2 = Bitmap.createBitmap(bitmap, x, y, width, height);
                    canvas.drawBitmap(b1, 0, 0, paint);
                    b1 = null;
                    canvas.drawBitmap(b2, 0, canvas.getHeight() - stepHeight, paint);
                    b2 = null;
                } else if(mVariant==2){
                    Bitmap b1 = getTriangleMaskedBitmapUsingPorterDuff(bitmap,
                            new Point(bitmap.getWidth()/2-stepWidth,bitmap.getHeight()/2-stepHeight),
                            new Point(bitmap.getWidth()/2+stepWidth,bitmap.getHeight()/2+stepHeight),
                            new Point(bitmap.getWidth()/2+stepWidth,bitmap.getHeight()/2-stepHeight));
                    canvas.drawBitmap(b1, bitmap.getWidth()-stepWidth*2, 0, paint);
                    Bitmap b2 = getTriangleMaskedBitmapUsingPorterDuff(bitmap,
                            new Point(bitmap.getWidth()/2-stepWidth,bitmap.getHeight()/2-stepHeight),
                            new Point(bitmap.getWidth()/2+stepWidth,bitmap.getHeight()/2+stepHeight),
                            new Point(bitmap.getWidth()/2-stepWidth,bitmap.getHeight()/2+stepHeight));
                    canvas.drawBitmap(b2, 0, bitmap.getHeight()-stepHeight*2, paint);
                } else if(mVariant==3){
                    Bitmap b1 = getTriangleMaskedBitmapUsingPorterDuff(bitmap,
                            new Point(bitmap.getWidth()/2+stepWidth,bitmap.getHeight()/2-stepHeight),
                            new Point(bitmap.getWidth()/2-stepWidth,bitmap.getHeight()/2+stepHeight),
                            new Point(bitmap.getWidth()/2-stepWidth,bitmap.getHeight()/2-stepHeight));
                    canvas.drawBitmap(b1, 0, 0, paint);
                    Bitmap b2 = getTriangleMaskedBitmapUsingPorterDuff(bitmap,
                            new Point(bitmap.getWidth()/2+stepWidth,bitmap.getHeight()/2-stepHeight),
                            new Point(bitmap.getWidth()/2-stepWidth,bitmap.getHeight()/2+stepHeight),
                            new Point(bitmap.getWidth()/2+stepWidth,bitmap.getHeight()/2+stepHeight));
                    canvas.drawBitmap(b2, bitmap.getWidth()-stepWidth*2, bitmap.getHeight()-stepHeight*2, paint);
                }
            }
            else{
                canvas.drawBitmap(bitmap,0,0,paint);
            }

        }
        return bitmap;
    }

    public static Bitmap getTriangleMaskedBitmapUsingPorterDuff(Bitmap source, Point p1, Point p2, Point p3) {
        if (source == null) {
            return null;
        }

        int minX = Math.min(p1.x, Math.min(p2.x,p3.x));
        int minY = Math.min(p1.y, Math.min(p2.y,p3.y));
        int maxX = Math.max(p1.x, Math.max(p2.x,p3.x));
        int maxY = Math.max(p1.y, Math.max(p2.y,p3.y));

        Bitmap targetBitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);

        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        final int color = 0xff424242;

        final Rect rect = new Rect(0, 0, source.getWidth(), source.getHeight());

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        final Path path = new Path();
        //draw triangle
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        path.close();
        canvas.drawPath(path,paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, rect, rect, paint);
        Bitmap cropedBitmap = Bitmap.createBitmap(targetBitmap, minX, minY, maxX-minX, maxY-minY);
        targetBitmap.recycle();
        return cropedBitmap;
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
        this.framesCount = count;
    }
}
