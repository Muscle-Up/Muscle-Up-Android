package com.example.muscleup.model.callback;

import android.graphics.Bitmap;
import android.net.Uri;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface LoadPoseImageListener {
    void load(RequestBody image, Bitmap bitmap);
}
