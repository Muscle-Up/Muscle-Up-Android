package com.example.muscleup.model;

import com.example.muscleup.model.callback.ChangePasswordListener;
import com.example.muscleup.model.service.ChangePasswordService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordModel {

    private ChangePasswordListener changePasswordListener;
    private ChangePasswordService changePasswordService;

    public ChangePasswordModel(ChangePasswordListener changePasswordListener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://15.165.38.79:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        changePasswordService = retrofit.create(ChangePasswordService.class);
        this.changePasswordListener = changePasswordListener;
    }

    public void changePassword(String token, String password) {
        Call<Void> call = changePasswordService.changePassword(token, password);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    if (response.code() == 401) changePasswordListener.onWrongToken();
                    return;
                }
                changePasswordListener.onSuccess();
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {

            }
        });
    }
}
