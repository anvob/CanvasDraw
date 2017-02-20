package com.anvob.canvasdraw;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by anvob on 17.02.2017.
 */

public class SquareView extends RelativeLayout{
    public SquareView(Context context) {
        super(context);
    }

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
