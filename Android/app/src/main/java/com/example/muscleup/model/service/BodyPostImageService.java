package com.example.muscleup.model.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface BodyPostImageService {
    @GET("body/image/{imageName}")
    Call<byte[]> getBodyPostImage(
            @Header("Authorization") String token,
            @Path("imageName") String imageName);
}