package com.example.muscleup.model;

import com.example.muscleup.model.callback.LoadBodyPostImageListener;
import com.example.muscleup.model.callback.LoadBodyPostListener;
import com.example.muscleup.model.data.BodyPost;
import com.example.muscleup.model.service.BodyPostImageService;
import com.example.muscleup.model.service.BodyPostService;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BodyPostModel {

    private BodyPostService bodyPostService;
    private BodyPostImageService bodyPostImageService;

    public BodyPostModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://15.165.38.79/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        bodyPostService = retrofit.create(BodyPostService.class);
        bodyPostImageService = retrofit.create(BodyPostImageService.class);
    }

    public void getBodyPost(String token, LoadBodyPostListener loadBodyPostListener) {
        Call<List<BodyPost>> call = bodyPostService.getBodyPost(token);
        call.enqueue(new Callback<List<BodyPost>>() {
            @Override
            public void onResponse(@NotNull Call<List<BodyPost>> call, @NotNull Response<List<BodyPost>> response) {

            }

            @Override
            public void onFailure(@NotNull Call<List<BodyPost>> call, @NotNull Throwable t) {

            }
        });
    }

    public void getBodyPostImage(String token, String imageName, LoadBodyPostImageListener loadBodyPostImageListener) {
        Call<byte[]> call = bodyPostImageService.getBodyPostImage(token, imageName);
        call.enqueue(new Callback<byte[]>() {
            @Override
            public void onResponse(@NotNull Call<byte[]> call, @NotNull Response<byte[]> response) {

            }

            @Override
            public void onFailure(@NotNull Call<byte[]> call, @NotNull Throwable t) {

            }
        });
    }
}
