package com.example.muscleup.model.callback;

public interface LoadImageListener {
    void onSuccess(byte[] image, int requestType);

    void onWrongToken();
}
