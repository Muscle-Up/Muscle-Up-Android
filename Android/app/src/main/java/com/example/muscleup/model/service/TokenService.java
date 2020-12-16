package com.example.muscleup.model.service;

import com.example.muscleup.model.data.Login;
import com.example.muscleup.model.data.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TokenService {
    @POST("auth")
    Call<Token> login(@Body Login login);

    @GET("auth")
    Call<Token> getNewToken(@Header("Authorization") String refreshToken);
}
