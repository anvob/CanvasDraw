package com.anvob.canvasdraw.screen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.anvob.canvasdraw.common.Slide;
import com.anvob.canvasdraw.common.TransitionFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anvob on 22.02.2017.
 */

public class DrawView extends ImageView {

    private int mCurFrame;
    private int mCurSlide;
    private int mCurSlideFrame;
    private Bitmap mBitmap;
    private Paint mPaint;
    private List<Slide> mSlideList;
    private List<TransitionFilter> mFilterList;

    public void setCurrentFrame(int frameNum) {
        this.mCurFrame = frameNum;
    }

    public int getCurrentFrame() {
        return mCurFrame;
    }

    public void addFilters(List<TransitionFilter> list) {
        mFilterList.clear();
        mFilterList.addAll(mFilterList.size(), list);
    }

    public void addSlides(List<Slide> list) {
        mSlideList.addAll(mSlideList.size(), list);
    }

    public DrawView(Context context) {
        super(context);
        mCurFrame = 0;
        mCurSlide = 0;
        mFilterList = new ArrayList<>();
        mSlideList = new ArrayList<>();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCurFrame = 0;
        mCurSlide = 0;
        mFilterList = new ArrayList<>();
        mSlideList = new ArrayList<>();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mCurFrame = 0;
        mCurSlide = 0;
        mFilterList = new ArrayList<>();
        mSlideList = new ArrayList<>();
    }

    public void moveToStart() {
        mCurSlide = 0;
        mCurSlideFrame = 0;
        mCurFrame = 0;
        invalidate();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurSlide < mSlideList.size()) {
            Slide s = mSlideList.get(mCurSlide);
            int slideFrameCount = s.getFramesCount();
            TransitionFilter tf = null;
            if (mCurSlide < mFilterList.size()) {
                tf = mFilterList.get(mCurSlide);
                slideFrameCount += tf.getFramesCount();
            }
            if (mCurSlideFrame < s.getFramesCount()) {
                canvas.drawBitmap(s.getBitmap(), 0, 0, mPaint);
            } else {
                if (tf != null) {
                    if (mCurSlideFrame < slideFrameCount) {
                        tf.paintNext(
                                canvas,
                                mSlideList.get(mCurSlide).getBitmap(),
                                mSlideList.get(mCurSlide + 1).getBitmap(),
                                mCurSlideFrame - s.getFramesCount());
                    }
                }
            }
            mCurFrame++;
            mCurSlideFrame++;
            if (mCurSlideFrame >= slideFrameCount) {
                mCurSlideFrame = 0;
                mCurSlide++;
            }
            this.invalidate();
        }
    }
}
