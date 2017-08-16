package com.anvob.canvasdraw.screen;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.anvob.canvasdraw.R;
import com.anvob.canvasdraw.common.Slide;
import com.anvob.canvasdraw.common.TransitionFilter;
import com.anvob.canvasdraw.filters.transition.CurtainSlideInFilter;
import com.anvob.canvasdraw.filters.transition.FadeInSlideFilter;
import com.anvob.canvasdraw.filters.transition.PullInOutFilter;
import com.anvob.canvasdraw.filters.transition.RoundSlideInFilter;
import com.anvob.canvasdraw.filters.transition.SlideInOutFilter;
import com.anvob.canvasdraw.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    DrawView dw;
    SeekBar durationBar;
    SeekBar animationBar;
    Button mBtnSlideOutIn;
    Button mBtnRoundSlideIn;
    Button mBtnPullInOut;
    Button mBtnCurtain;
    Button mBtnSlideOut;
    Button mBtnFadeInSlide;
    Button mBtnMix;
    TextView mDurationValue;
    TextView mAnimationValue;
    TextView mFilterType;
    Random random;
    private List<Slide> mSlideList;
    private List<TransitionFilter> mFilterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSlideList = new ArrayList<>();
        mFilterList = new ArrayList<>();
        random = new Random();
        initView();
        //initSlidesRes();
        initSlidesAssets(true);
        shuffleFilters();
        dw.addFilters(mFilterList);
        dw.addSlides(mSlideList);
        dw.setRepeat(true);
        mFilterType.setText(R.string.title_mix);
    }

    void initView() {
        dw = (DrawView) findViewById(R.id.image);
        durationBar = (SeekBar) findViewById(R.id.seekBar);
        durationBar.setOnSeekBarChangeListener(this);
        durationBar.setProgress(50);
        animationBar = (SeekBar) findViewById(R.id.seekbar_animation);
        animationBar.setOnSeekBarChangeListener(this);
        animationBar.setProgress(10);
        mBtnSlideOutIn = (Button) findViewById(R.id.btn_soi);
        mBtnSlideOutIn.setOnClickListener(this);
        mBtnRoundSlideIn = (Button) findViewById(R.id.btn_rsi);
        mBtnRoundSlideIn.setOnClickListener(this);
        mBtnPullInOut = (Button) findViewById(R.id.btn_pio);
        mBtnPullInOut.setOnClickListener(this);
        mBtnCurtain = (Button) findViewById(R.id.btn_c);
        mBtnCurtain.setOnClickListener(this);
        mBtnSlideOut = (Button) findViewById(R.id.btn_so);
        mBtnSlideOut.setOnClickListener(this);
        mBtnFadeInSlide = (Button) findViewById(R.id.btn_fis);
        mBtnFadeInSlide.setOnClickListener(this);
        mBtnMix = (Button) findViewById(R.id.btn_mix);
        mBtnMix.setOnClickListener(this);
        mDurationValue = (TextView) findViewById(R.id.value_duration);
        mDurationValue.setText("50");
        mAnimationValue = (TextView) findViewById(R.id.value_animation);
        mAnimationValue.setText("20");
        mFilterType = (TextView) findViewById(R.id.filter_type);
    }

    void initSlidesRes() {

        /*Resources res = getResources();
        int length = res.getDisplayMetrics().widthPixels;
        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.b1);
        b1 = Bitmap.createScaledBitmap(b1, length, length, true);
        Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.b2);
        b2 = Bitmap.createScaledBitmap(b2, length, length, true);
        Bitmap b3 = BitmapFactory.decodeResource(getResources(), R.drawable.b3);
        b3 = Bitmap.createScaledBitmap(b3, length, length, true);
        Bitmap b4 = BitmapFactory.decodeResource(getResources(), R.drawable.b4);
        b4 = Bitmap.createScaledBitmap(b4, length, length, true);
        Bitmap b5 = BitmapFactory.decodeResource(getResources(), R.drawable.b5);
        b5 = Bitmap.createScaledBitmap(b5, length, length, true);
        Bitmap b6 = BitmapFactory.decodeResource(getResources(), R.drawable.b6);
        b6 = Bitmap.createScaledBitmap(b6, length, length, true);
        Bitmap b7 = BitmapFactory.decodeResource(getResources(), R.drawable.b7);
        b7 = Bitmap.createScaledBitmap(b7, length, length, true);
        Bitmap b8 = BitmapFactory.decodeResource(getResources(), R.drawable.b8);
        b8 = Bitmap.createScaledBitmap(b8, length, length, true);
        Bitmap b9 = BitmapFactory.decodeResource(getResources(), R.drawable.b9);
        b9 = Bitmap.createScaledBitmap(b9, length, length, true);
        Bitmap b10 = BitmapFactory.decodeResource(getResources(), R.drawable.b10);
        b10 = Bitmap.createScaledBitmap(b10, length, length, true);
        Bitmap b11 = BitmapFactory.decodeResource(getResources(), R.drawable.b11);
        b11 = Bitmap.createScaledBitmap(b11, length, length, true);
        Bitmap b12 = BitmapFactory.decodeResource(getResources(), R.drawable.b12);
        b12 = Bitmap.createScaledBitmap(b12, length, length, true);

        mSlideList.add(new Slide(b1, 50));
        mSlideList.add(new Slide(b2, 50));
        mSlideList.add(new Slide(b3, 50));
        mSlideList.add(new Slide(b4, 50));
        mSlideList.add(new Slide(b5, 50));
        mSlideList.add(new Slide(b6, 50));
        mSlideList.add(new Slide(b7, 50));
        mSlideList.add(new Slide(b8, 50));
        mSlideList.add(new Slide(b9, 50));
        mSlideList.add(new Slide(b10, 50));
        mSlideList.add(new Slide(b11, 50));
        mSlideList.add(new Slide(b12, 50));*/
    }

    void initSlidesAssets(boolean isRepeat) {

        ImageUtils mImageUtils = new ImageUtils(this);
        Resources res = getResources();
        int length = res.getDisplayMetrics().widthPixels;
        //length = 320;
        Bitmap b1 = mImageUtils.decodeSampledBitmapFromAssets("b1.jpg", length, length);
        Bitmap b2 = mImageUtils.decodeSampledBitmapFromAssets("b2.jpg", length, length);
        Bitmap b3 = mImageUtils.decodeSampledBitmapFromAssets("b3.jpg", length, length);
        Bitmap b4 = mImageUtils.decodeSampledBitmapFromAssets("b4.jpg", length, length);
        Bitmap b5 = mImageUtils.decodeSampledBitmapFromAssets("b5.jpg", length, length);
        Bitmap b6 = mImageUtils.decodeSampledBitmapFromAssets("b6.jpg", length, length);
        Bitmap b7 = mImageUtils.decodeSampledBitmapFromAssets("b7.jpg", length, length);
        Bitmap b8 = mImageUtils.decodeSampledBitmapFromAssets("b8.jpg", length, length);
        Bitmap b9 = mImageUtils.decodeSampledBitmapFromAssets("b9.jpg", length, length);
        Bitmap b10 = mImageUtils.decodeSampledBitmapFromAssets("b10.jpg", length, length);
        Bitmap b11 = mImageUtils.decodeSampledBitmapFromAssets("b11.jpg", length, length);
        Bitmap b12 = mImageUtils.decodeSampledBitmapFromAssets("b12.jpg", length, length);

        mSlideList.clear();

        mSlideList.add(new Slide(b1, 50));
        mSlideList.add(new Slide(b2, 50));
        mSlideList.add(new Slide(b3, 50));
        mSlideList.add(new Slide(b4, 50));
        mSlideList.add(new Slide(b5, 50));
        mSlideList.add(new Slide(b6, 50));
        mSlideList.add(new Slide(b7, 50));
        mSlideList.add(new Slide(b8, 50));
        mSlideList.add(new Slide(b9, 50));
        mSlideList.add(new Slide(b10, 50));
        mSlideList.add(new Slide(b11, 50));
        mSlideList.add(new Slide(b12, 50));
        if(isRepeat) {
            mSlideList.add(new Slide(b1, 0));
        }
    }

    int getAnimationSpeed() {
        if (animationBar != null) {
            return animationBar.getProgress() + 10;
        } else {
            return 20;
        }
    }

    void shuffleFilters() {
        if (mSlideList != null && mFilterList != null) {
            mFilterList.clear();
            for (int i = 0; i < mSlideList.size() - 1; i++) {
                TransitionFilter tf = getFilterById(random.nextInt(6));
                tf.setFramesCount(getAnimationSpeed());
                mFilterList.add(tf);
            }
        }
    }

    TransitionFilter getFilterById(int id) {
        switch (id) {
            case 0:
                return SlideInOutFilter.getSlideInOutFilter(random.nextInt(4));
            case 1:
                return RoundSlideInFilter.getRoundSlideInFilter(random.nextInt(4));
            case 2:
                return PullInOutFilter.getPullInOutFilter(random.nextInt(8), random.nextInt(4));
            case 3:
                return CurtainSlideInFilter.getCurtainFilter(random.nextInt(4), random.nextInt(4));
            case 4:
                return SlideInOutFilter.getSlideOutFilter(random.nextInt(4));
            case 5:
                return FadeInSlideFilter.getFadeInSlideFilter(random.nextInt(4));
            default:
                return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (mSlideList != null && mFilterList != null) {
            mFilterList.clear();
            switch (view.getId()) {
                case R.id.btn_soi:
                    for (int i = 0; i < mSlideList.size() - 1; i++) {
                        TransitionFilter tf = SlideInOutFilter.getSlideInOutFilter(random.nextInt(4));
                        tf.setFramesCount(getAnimationSpeed());
                        mFilterList.add(tf);
                    }
                    mFilterType.setText(R.string.title_soi);
                    break;
                case R.id.btn_rsi:
                    for (int i = 0; i < mSlideList.size() - 1; i++) {
                        TransitionFilter tf = RoundSlideInFilter.getRoundSlideInFilter(random.nextInt(4));
                        tf.setFramesCount(getAnimationSpeed());
                        mFilterList.add(tf);
                    }
                    mFilterType.setText(R.string.title_rsi);
                    break;
                case R.id.btn_pio:
                    for (int i = 0; i < mSlideList.size() - 1; i++) {
                        TransitionFilter tf = PullInOutFilter.getPullInOutFilter(random.nextInt(8), random.nextInt(4));
                        tf.setFramesCount(getAnimationSpeed());
                        mFilterList.add(tf);
                    }
                    mFilterType.setText(R.string.title_pio);
                    break;
                case R.id.btn_c:
                    for (int i = 0; i < mSlideList.size() - 1; i++) {
                        TransitionFilter tf = CurtainSlideInFilter.getCurtainFilter(random.nextInt(4), random.nextInt(4));
                        tf.setFramesCount(getAnimationSpeed());
                        mFilterList.add(tf);
                    }
                    mFilterType.setText(R.string.title_curtain);
                    break;
                case R.id.btn_so:
                    for (int i = 0; i < mSlideList.size() - 1; i++) {
                        TransitionFilter tf = SlideInOutFilter.getSlideOutFilter(random.nextInt(4));
                        tf.setFramesCount(getAnimationSpeed());
                        mFilterList.add(tf);
                    }
                    mFilterType.setText(R.string.title_so);
                    break;
                case R.id.btn_fis:
                    for (int i = 0; i < mSlideList.size() - 1; i++) {
                        TransitionFilter tf = FadeInSlideFilter.getFadeInSlideFilter(random.nextInt(4));
                        tf.setFramesCount(getAnimationSpeed());
                        mFilterList.add(tf);
                    }
                    mFilterType.setText(R.string.title_fis);
                    break;
                case R.id.btn_mix:
                    shuffleFilters();
                    mFilterType.setText(R.string.title_mix);
                    break;
            }
            dw.addFilters(mFilterList);
            startSlideShow();
        }
    }

    void startSlideShow() {
        dw.moveToStart();
    }

    void updateSlideListDuration(int duration) {
        if (mSlideList != null && mSlideList.size() > 0) {
            for (Slide s : mSlideList) {
                s.setFramesCount(duration);
                startSlideShow();
            }
        }
    }

    void updateAnimationSpeed(int duration) {
        if (mFilterList != null && mFilterList.size() > 0) {
            for (TransitionFilter f : mFilterList) {
                f.setFramesCount(duration);
                startSlideShow();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                startActivity(new Intent(this,HelpActivity.class));
                return true;

            default:
                // Not one of ours. Perform default menu processing
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.getId() == R.id.seekBar) {
            mDurationValue.setText(String.valueOf(seekBar.getProgress() + 10));
            updateSlideListDuration(seekBar.getProgress() + 10);
        } else if (seekBar.getId() == R.id.seekbar_animation) {
            mAnimationValue.setText(String.valueOf(seekBar.getProgress() + 10));
            updateAnimationSpeed(seekBar.getProgress() + 10);
        }
    }
}