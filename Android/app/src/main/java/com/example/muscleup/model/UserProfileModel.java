package com.example.muscleup.model;

import androidx.annotation.NonNull;

import com.example.muscleup.model.callback.EditUserInfoListener;
import com.example.muscleup.model.callback.LoadUserInfoListener;
import com.example.muscleup.model.data.UserProfile;
import com.example.muscleup.model.data.UserProfileRequest;
import com.example.muscleup.model.service.UserProfileService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProfileModel {

    public static final int ERROR_LOAD_PROFILE = 0;
    public static final int ERROR_EDIT_PROFILE = 1;

    private UserProfileService userProfileService;

    public UserProfileModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("BASE_URL")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userProfileService = retrofit.create(UserProfileService.class);
    }

    public void getUserProfile(String token, LoadUserInfoListener callback) {
        Call<UserProfile> call = userProfileService.getUserProfile(token);

        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(@NonNull Call<UserProfile> call, @NotNull Response<UserProfile> response) {
                if (!response.isSuccessful()){
                    if(response.code() == 401) callback.onWrongToken();
                    return;
                }
                callback.loadUserInfo(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<UserProfile> call, @NotNull Throwable t) {

            }
        });
    }

    public void editUserProfile(String token, UserProfileRequest profile, byte[] image, EditUserInfoListener callback) {
        Call<Void> call = userProfileService.editUserProfile(token, profile, image);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if(!response.isSuccessful()){
                    if(response.code() == 401) callback.onWrongToken();
                    return;
                }
                callback.onSuccess();
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {

            }
        });
    }
}