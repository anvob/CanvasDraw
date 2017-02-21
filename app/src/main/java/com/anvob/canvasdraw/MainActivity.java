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
import java.util.Random;

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
            Random random = new Random();
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mSlideList = new ArrayList<Slide>();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            //Bitmap b1 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Download/1.png");
            //b1 = Bitmap.createScaledBitmap(b1,1080,1080,true);
            //Bitmap b2 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Download/i.jpg");
            //b2 = Bitmap.createScaledBitmap(b2,1080,1080,true);
            //Bitmap b3 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Download/image.jpeg");
            //b3 = Bitmap.createScaledBitmap(b3,1080,1080,true);
            Bitmap b1 = BitmapFactory.decodeResource(getResources(),R.drawable.b1,options);
            b1 = Bitmap.createScaledBitmap(b1,1080,1080,true);
            Bitmap b2 = BitmapFactory.decodeResource(getResources(),R.drawable.b2,options);
            b2 = Bitmap.createScaledBitmap(b2,1080,1080,true);
            Bitmap b3 = BitmapFactory.decodeResource(getResources(),R.drawable.b3,options);
            b3 = Bitmap.createScaledBitmap(b3,1080,1080,true);
            Bitmap b4 = BitmapFactory.decodeResource(getResources(),R.drawable.b4,options);
            b4 = Bitmap.createScaledBitmap(b4,1080,1080,true);
            Bitmap b5 = BitmapFactory.decodeResource(getResources(),R.drawable.b5,options);
            b5 = Bitmap.createScaledBitmap(b5,1080,1080,true);
            mSlideList.add(new Slide(b1, 50));
            mSlideList.add(new Slide(b2, 50));
            mSlideList.add(new Slide(b3, 50));
            mSlideList.add(new Slide(b4, 50));
            mSlideList.add(new Slide(b5, 50));

            mFilterList = new ArrayList<TransitionFilter>();
            //1
            PullInOutFilter transFilter = new PullInOutFilter();
            int variant = random.nextInt(3);
            PullOutFilter pof = new PullOutFilter(20,variant);
            variant = random.nextInt(3);
            pof.setNextFilter(new SlideInFilter(20,variant));
            transFilter.setShowFilter(pof);
            mFilterList.add(transFilter);
            //2
            SlideInOutFilter transFilter2 = new SlideInOutFilter();
            variant = random.nextInt(3);
            transFilter2.setShowFilter(new SlideInFilter(20, variant));
            transFilter2.setHideFilter(new SlideOutFilter(20, variant));
            mFilterList.add(transFilter2);
            //3
            PullInOutFilter transFilter3 = new PullInOutFilter();
            variant = random.nextInt(3);
            PullInFilter pif = new PullInFilter(20,variant);
            variant = random.nextInt(3);
            pif.setNextFilter(new SlideInFilter(20,variant));
            transFilter3.setShowFilter(pif);
            mFilterList.add(transFilter3);
            //4
            SlideInOutFilter transFilter4 = new SlideInOutFilter();
            variant = random.nextInt(3);
            transFilter4.setShowFilter(new SlideInFilter(20, variant));
            mFilterList.add(transFilter4);
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
            Canvas canvas;
            private SurfaceHolder surfaceHolder;

            public DrawThread(SurfaceHolder surfaceHolder) {
                this.surfaceHolder = surfaceHolder;
            }

            public void setRunning(boolean running) {
                this.running = running;
            }

            @Override
            public void run() {
                canvas = null;
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
                        //canvas = null;
                        while (curFrame <= slideFrameCount) {
                            try {
                                canvas = surfaceHolder.lockCanvas(null);
                                if (canvas == null)
                                    continue;

                                //show current slide
                                if (curFrame <= sl.getFramesCount()) {
                                    canvas.drawBitmap(sl.getBitmap(), 0, 0, paint);
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