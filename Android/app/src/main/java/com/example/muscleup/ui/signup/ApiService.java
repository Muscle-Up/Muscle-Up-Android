package com.example.muscleup.ui.signup;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/user")
    Call<Void> register(@Query("name") String name,
                        @Query("age") int age,
                        @Query("sex") Sex sex,
                        @Query("height") int height,
                        @Query("weight") int weight,
                        @Query("email") String email,
                        @Query("password") String password,
                        @Query("image") byte[] image);
}
