package com.example.muscleup.model.service;

import com.example.muscleup.model.data.Graph;
import com.example.muscleup.model.data.GraphRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GraphService {
    @GET("graph/check")
    Call<Boolean> checkInputToday(
            @Header("Authorization") String token);

    @GET("graph/{graphType}")
    Call<List<Graph>> getGraph(
            @Header("Authorization") String token,
            @Path("graphType") String graphType
    );

    @POST("graph")
    Call<Void> postGraph(
            @Header("Authorization") String token,
            @Body GraphRequest graphRequest
    );

    @PUT("graph/{graphId}")
    Call<Void> putGraph(
            @Header("Authorization") String token,
            @Body GraphRequest graphRequest,
            @Path("graphId") int id
    );

    @DELETE("graph/{graphId}")
    Call<Void> deleteGraph(
            @Header("Authorization") String token,
            @Path("graphId") int id
    );
}