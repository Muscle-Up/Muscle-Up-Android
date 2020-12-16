package com.example.muscleup.model.service;

import com.example.muscleup.model.data.UserProfile;
import com.example.muscleup.model.data.UserProfileRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserProfileService {
    @GET("user")
    Call<UserProfile> getUserProfile(
            @Header("Authorization") String token);

    @POST("auth")
    Call<Void> editUserProfile(
            @Header("Authorization") String token,
            @Body UserProfileRequest userProfile,
            @Query("image") byte[] image);
}
