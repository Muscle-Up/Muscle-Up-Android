package com.example.muscleup.signup;

import android.service.autofill.UserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    @FormUrlEncoded
    @POST("/user")
    Call<Post> createUser(@Body Post userdata);

    @POST("/user")
    Call<Void> register(@Query("name") String name,
                        @Query("age") int age,
                        @Query("sex") Sex sex,
                        @Query("height") float height,
                        @Query("weight") float weight,
                        @Query("email") String email,
                        @Query("password") String password);
}
