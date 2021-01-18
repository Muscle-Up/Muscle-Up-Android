package com.example.muscleup.model.service;

import com.example.muscleup.model.data.ChangePasswordRequest;
import com.example.muscleup.ui.changePassword.ChangePasswordActivity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface ChangePasswordService {
    @PUT("user")
    Call<Void> changePassword(
            @Header("Authorization") String token,
            @Body ChangePasswordRequest ChangePasswordRequest);
}
