package com.uukeshov.findu;

/**
 * Created by admin on 14.04.2016.
 */

import android.graphics.Bitmap;
import android.util.Log;

public class ImageItem {
    String LOG_TAG = "myinstaLog6";
    private Bitmap image;

    public ImageItem(Bitmap image) {
        super();
        this.image = image;
        Log.d(LOG_TAG, "myinstaLog6 " + image.toString());
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}