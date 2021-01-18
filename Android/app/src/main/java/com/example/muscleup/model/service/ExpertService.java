package com.example.muscleup.model.service;

import com.example.muscleup.model.data.ExpertProfile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ExpertService {
    @GET("expert/my")
    Call<ExpertProfile> getMyExpertProfile(
            @Header("Authorization") String token);

    @Multipart
    @POST("expert")
    Call<Void> registerExpert(
            @Header("Authorization") String token,
            @Part("introduction") RequestBody intro,
            @Part("certificateName") RequestBody name,
            @Part("acquisitionDate") RequestBody date,
            @Part MultipartBody.Part certificateImage);
}
