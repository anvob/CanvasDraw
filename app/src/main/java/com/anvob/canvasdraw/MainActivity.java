package com.anvob.canvasdraw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.anvob.canvasdraw.Filters.PullInFilter;
import com.anvob.canvasdraw.Filters.PullInOutFilter;
import com.anvob.canvasdraw.Filters.PullOutFilter;
import com.anvob.canvasdraw.Filters.SlideInFilter;
import com.anvob.canvasdraw.Filters.SlideInOutFilter;
import com.anvob.canvasdraw.Filters.SlideOutFilter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    SurfaceView sw;
    RelativeLayout layout;
    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (RelativeLayout) findViewById(R.id.layout1);

        sw = new DrawView(this);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.addView(sw,p);
    }

    class DrawView extends SurfaceView implements SurfaceHolder.Callback {

        private DrawThread drawThread;
        Bitmap bitmap;
        Paint paint;
        SlideInFilter filter;
        private List<Slide> mSlideList;
        private List<TransitionFilter> mFilterList;

        public DrawView(Context context) {
            super(context);
            getHolder().addCallback(this);
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mSlideList = new ArrayList<Slide>();
            //Bitmap b1 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Download/1.png");
            //b1 = Bitmap.createScaledBitmap(b1,1080,1080,true);
            //Bitmap b2 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Download/i.jpg");
            //b2 = Bitmap.createScaledBitmap(b2,1080,1080,true);
            //Bitmap b3 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Download/image.jpeg");
            //b3 = Bitmap.createScaledBitmap(b3,1080,1080,true);
            Bitmap b1 = BitmapFactory.decodeResource(getResources(),R.drawable.b4);
            b1 = Bitmap.createScaledBitmap(b1,1080,1080,true);
            Bitmap b2 = BitmapFactory.decodeResource(getResources(),R.drawable.b2);
            b2 = Bitmap.createScaledBitmap(b2,1080,1080,true);
            Bitmap b3 = BitmapFactory.decodeResource(getResources(),R.drawable.b3);
            b3 = Bitmap.createScaledBitmap(b3,1080,1080,true);
            mSlideList.add(new Slide(b1, 50));
            mSlideList.add(new Slide(b2, 50));
            mSlideList.add(new Slide(b3, 50));


            mFilterList = new ArrayList<TransitionFilter>();
            //SlideInOutFilter transFilter = new SlideInOutFilter();
            //transFilter.setShowFilter(new SlideInFilter(20, 0));
            //transFilter.setHideFilter(new SlideOutFilter(20,0));
            PullInOutFilter transFilter = new PullInOutFilter();
            PullOutFilter pf = new PullOutFilter(20,3);
            pf.setNextFilter(new SlideInFilter(20,0 ));
            transFilter.setShowFilter(pf);
            mFilterList.add(transFilter);
            SlideInOutFilter transFilter2 = new SlideInOutFilter();
            transFilter2.setShowFilter(new SlideInFilter(20, 3));
            //transFilter2.setShowFilter(new PullInFilter(20,3));
            mFilterList.add(transFilter2);
        }
        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            drawThread = new DrawThread(getHolder());
            drawThread.setRunning(true);
            drawThread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean retry = true;
            drawThread.setRunning(false);
            while (retry) {
                try {
                    drawThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
        }

        class DrawThread extends Thread {

            private boolean running = false;
            private SurfaceHolder surfaceHolder;

            public DrawThread(SurfaceHolder surfaceHolder) {
                this.surfaceHolder = surfaceHolder;
            }

            public void setRunning(boolean running) {
                this.running = running;
            }

            @Override
            public void run() {
                Canvas canvas;
                int curFrame = 0;
                int slideFrameCount = 0;
                while (running) {
                    for (int i = 0; i < mSlideList.size(); i++) {
                        curFrame = 0;
                        slideFrameCount=0;
                        Slide sl = mSlideList.get(i);
                        slideFrameCount += sl.getFramesCount();
                        TransitionFilter filter = null;
                        if (i < mSlideList.size()-1) {
                            filter = mFilterList.get(i);
                            if (filter != null) {
                                slideFrameCount += filter.getFramesCount();
                            }
                        }
                        canvas = null;
                        while (curFrame <= slideFrameCount) {
                            try {
                                canvas = surfaceHolder.lockCanvas(null);
                                if (canvas == null)
                                    continue;

                                //show current slide
                                if (curFrame <= sl.getFramesCount()) {
                                    //if(curFrame==0) {
                                        //Bitmap current_bitmap = Bitmap.createScaledBitmap(sl.getBitmap(),
                                        //                                                  canvas.getWidth(),
                                        //                                                  canvas.getHeight(), true);
                                        canvas.drawBitmap(sl.getBitmap(), 0, 0, paint);
                                        //current_bitmap=null;
                                    //}
                                    curFrame++;
                                } else {
                                    if (filter != null) {
                                        filter.paintNext(
                                                canvas,
                                                mSlideList.get(i).getBitmap(),
                                                mSlideList.get(i + 1).getBitmap(),
                                                curFrame - sl.getFramesCount());
                                        curFrame++;
                                    }
                                }
                                canvas.drawText(curFrame+"",50,50,paint);

                            } finally {
                                if (canvas != null) {
                                    surfaceHolder.unlockCanvasAndPost(canvas);
                                }
                            }
                        }
                    }
                    running=false;
                }

            }

        }
    }
}