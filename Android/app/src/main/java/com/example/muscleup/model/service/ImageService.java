package com.example.muscleup.model.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ImageService {
    @GET("image/{imageName}")
    Call<byte[]> getImage(
            @Header("Authorization") String token,
            @Path("imageName") String imageName);
}
