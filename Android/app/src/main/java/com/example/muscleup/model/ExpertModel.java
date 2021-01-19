package com.example.muscleup.model;

import android.util.Log;

import com.example.muscleup.model.callback.LoadMyExpertProfileListener;
import com.example.muscleup.model.callback.RegisterExpertListener;
import com.example.muscleup.model.data.ExpertProfile;
import com.example.muscleup.model.service.ExpertService;

import org.jetbrains.annotations.NotNull;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExpertModel {

    public static final int IMAGE_TYPE_PROFILE = 1000;
    public static final int IMAGE_TYPE_CERTIFICATE = 1001;
    private ExpertService expertService;

    public ExpertModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://15.165.38.79:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        expertService = retrofit.create(ExpertService.class);
    }

    public void getMyExpertProfile(String token, LoadMyExpertProfileListener loadMyExpertProfileListener) {
        Call<ExpertProfile> call = expertService.getMyExpertProfile(token);
        call.enqueue(new Callback<ExpertProfile>() {
            @Override
            public void onResponse(@NotNull Call<ExpertProfile> call, @NotNull Response<ExpertProfile> response) {
                if (!response.isSuccessful()) {
                    Log.d("ExpertModel", "onResponse: "+response.code()+response.message());
                    if (response.code() == 401) loadMyExpertProfileListener.onWrongToken();
                    return;
                }
                loadMyExpertProfileListener.loadMyExpertProfile(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<ExpertProfile> call, @NotNull Throwable t) {

            }
        });
    }

    public void registerExpert(
            String token, RequestBody intro, RequestBody name, RequestBody date,
            MultipartBody.Part certificateImage, RegisterExpertListener registerExpertListener) {

        Call<Void> call = expertService.registerExpert(token, intro, name, date, certificateImage);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    Log.d("ExpertModel", "onResponse: " + response.code() + "/" + response.message());
                    Log.d("ExpertModel", "onResponse: " + call.request().toString());
                    if (response.code() == 401) registerExpertListener.onWrongToken();
                    return;
                }
                registerExpertListener.onSuccess();
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Log.d("ExpertModel", "onFailure: " + t.getMessage());
            }
        });
    }
}
