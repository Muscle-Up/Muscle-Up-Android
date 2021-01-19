package com.example.muscleup.model;

import android.util.Log;

import com.example.muscleup.model.callback.CheckPoseListener;
import com.example.muscleup.model.data.Pose;
import com.example.muscleup.model.service.PoseService;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PoseModel {

    private PoseService poseService;
    private CheckPoseListener checkPoseListener;

    public PoseModel(CheckPoseListener checkPoseListener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://15.165.38.79:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        poseService = retrofit.create(PoseService.class);
        this.checkPoseListener = checkPoseListener;
    }

    public void checkPose(String token, RequestBody image, int position) {
        Call<List<Pose>> call = poseService.checkPose(token, image);
        call.enqueue(new Callback<List<Pose>>() {
            @Override
            public void onResponse(@NotNull Call<List<Pose>> call, @NotNull Response<List<Pose>> response) {
                if (!response.isSuccessful()) {
                    Log.d("PoseModel", "onResponse: " + response.code());
                    Log.d("PoseModel", "onResponse: " + call.request().toString());
                    if (response.code() == 401) checkPoseListener.onWrongToken();
                    return;
                }
                checkPoseListener.onSuccess(response.body(), position);
            }

            @Override
            public void onFailure(@NotNull Call<List<Pose>> call, @NotNull Throwable t) {

            }
        });
    }
}
