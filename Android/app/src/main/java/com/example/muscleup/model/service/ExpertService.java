package com.example.muscleup.model.service;

import com.example.muscleup.model.data.ExpertProfile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ExpertService {
    @GET("expert/my")
    Call<ExpertProfile> getMyExpertProfile(
            @Header("Authorization") String token);

    @POST("expert")
    Call<Void> registerExpert(
            @Header("Authorization") String token,
            @Query("introduction") String intro,
            @Query("certificateName") String name,
            @Query("acquisitionDate") String date,
            @Query("certificateImage") byte[] certificateImage,
            @Query("image") byte[] image);
}
