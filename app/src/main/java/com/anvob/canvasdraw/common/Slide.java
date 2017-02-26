package com.anvob.canvasdraw.common;

import android.graphics.Bitmap;

/**
 * Created by anvob on 17.02.2017.
 */

public class Slide {
    private Bitmap mBitmap;
    private int mFramesCount;

    public Slide(Bitmap bitmap, int frames) {
        this.mBitmap = bitmap;
        this.mFramesCount = frames;
    }

    public void setFramesCount(int frames) {
        this.mFramesCount = frames;
    }

    public int getFramesCount() {
        return mFramesCount;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }
}
