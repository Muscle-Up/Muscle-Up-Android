package com.example.muscleup.model.service;

import com.example.muscleup.model.data.UserProfile;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

public interface UserProfileService {
    @GET("user")
    Call<UserProfile> getUserProfile(
            @Header("Authorization") String token);

    @Multipart
    @PUT("user/update")
    Call<Void> editUserProfile(
            @Header("Authorization") String token,
            @Part("name") RequestBody name,
            @Part("age") RequestBody age,
            @Part("height") RequestBody height,
            @Part("weight") RequestBody weight,
            @Part MultipartBody.Part image);
}
