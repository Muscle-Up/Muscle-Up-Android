package com.example.muscleup.model.data;

import android.graphics.Bitmap;

import okhttp3.RequestBody;

public class PoseItem {

    public static final int ITEM_POSE_IMAGE = 0;
    public static final int ITEM_UPLOAD = 1;

    private int viewType;
    private RequestBody image;
    private Bitmap bitmap;

    public PoseItem(int viewType, RequestBody image, Bitmap bitmap) {
        this.viewType = viewType;
        this.image = image;
        this.bitmap = bitmap;
    }

    public int getViewType() {
        return viewType;
    }

    public RequestBody getImage() {
        return image;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
