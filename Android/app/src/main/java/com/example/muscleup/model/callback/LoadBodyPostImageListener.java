package com.example.muscleup.model.callback;

public interface LoadBodyPostImageListener {
    void onSuccess(byte[] image);

    void onWrongToken();
}
