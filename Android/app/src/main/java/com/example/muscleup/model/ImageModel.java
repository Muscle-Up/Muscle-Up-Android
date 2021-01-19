package com.example.muscleup.model;

import android.util.Log;

import com.example.muscleup.model.callback.LoadImageListener;
import com.example.muscleup.model.service.ImageService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageModel {

    private ImageService imageService;

    public ImageModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://15.165.38.79:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        imageService = retrofit.create(ImageService.class);
    }

    public void getImage(String token, String imageName, int requestType, LoadImageListener loadImageListener) {
        Call<byte[]> call = imageService.getImage(token, imageName);
        call.enqueue(new Callback<byte[]>() {
            @Override
            public void onResponse(@NotNull Call<byte[]> call, @NotNull Response<byte[]> response) {
                if (!response.isSuccessful()) {
                    Log.d("ImageModel", "onResponse: "+call.request().toString());
                    if(response.code() == 401) loadImageListener.onWrongToken();
                    return;
                }
                loadImageListener.onSuccess(response.body(), requestType);
            }

            @Override
            public void onFailure(@NotNull Call<byte[]> call, @NotNull Throwable t) {

            }
        });
    }
}
