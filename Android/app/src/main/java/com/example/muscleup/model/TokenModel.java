package com.example.muscleup.model;

import com.example.muscleup.model.callback.LoadTokenListener;
import com.example.muscleup.model.data.Login;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.service.TokenService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenModel {

    private TokenService tokenService;

    public TokenModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://15.165.38.79/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tokenService = retrofit.create(TokenService.class);
    }

    public void login(Login login, LoadTokenListener loadTokenListener) {
        Call<Token> call = tokenService.login(login);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(@NotNull Call<Token> call, @NotNull Response<Token> response) {
                if(!response.isSuccessful()) {
                    loadTokenListener.onFail();
                    return;
                }

                loadTokenListener.loadToken(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<Token> call, @NotNull Throwable t) {

            }
        });
    }

    public void getNewToken(String refreshToken, LoadTokenListener loadTokenListener) {
        Call<Token> call = tokenService.getNewToken(refreshToken);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(@NotNull Call<Token> call, @NotNull Response<Token> response) {
                if(!response.isSuccessful()) {
                    loadTokenListener.onFail();
                    return;
                }

                loadTokenListener.loadToken(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<Token> call, @NotNull Throwable t) {

            }
        });
    }
}
