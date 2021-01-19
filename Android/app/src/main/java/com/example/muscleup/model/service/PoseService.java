package com.example.muscleup.model.service;

import com.example.muscleup.model.data.Pose;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface PoseService {

    @GET("pose")
    Call<List<Pose>> checkPose(
            @Header("Authorization") String token,
            @Query("image") RequestBody image);
}