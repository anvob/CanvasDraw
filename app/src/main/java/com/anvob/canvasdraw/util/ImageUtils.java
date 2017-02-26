package com.anvob.canvasdraw.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Created by anvob on 26.02.17.
 */
public class ImageUtils {

    private Context mContext;

    public ImageUtils(Context context) {
        mContext = context;
    }

    public Bitmap decodeSampledBitmapFromAssets(String assetName, int reqWidth, int reqHeight) {
        try {
            InputStream ims = mContext.getAssets().open(assetName);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(ims, null, options);
            ims = mContext.getAssets().open(assetName);
            Bitmap temp = BitmapFactory.decodeStream(ims);
            return Bitmap.createScaledBitmap(temp, reqWidth, reqHeight, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
