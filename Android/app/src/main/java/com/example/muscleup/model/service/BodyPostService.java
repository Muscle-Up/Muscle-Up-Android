package com.example.muscleup.model.service;

import com.example.muscleup.model.data.BodyPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface BodyPostService {
    @GET("body")
    Call<List<BodyPost>> getBodyPost(
            @Header("Authorization") String token);
}