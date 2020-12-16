package com.example.muscleup.model;

import androidx.annotation.NonNull;

import com.example.muscleup.model.callback.CheckInputListener;
import com.example.muscleup.model.callback.EditGraphListener;
import com.example.muscleup.model.callback.LoadGraphListener;
import com.example.muscleup.model.data.Graph;
import com.example.muscleup.model.service.GraphService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GraphModel {

    public static final int MUSCLE_MATH_GRAPH = 0;
    public static final int BODY_FAT_MASS_GRAPH = 1;
    public static final int WEIGHT_GRAPH = 2;

    public static final int ERROR_CHECK_INPUT = 1001;
    public static final int ERROR_LOAD_GRAPH = 1002;
    public static final int ERROR_POST_GRAPH = 1003;
    public static final int ERROR_PUT_GRAPH = 1004;
    public static final int ERROR_DELETE_GRAPH = 1005;

    private GraphService graphService;

    public GraphModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("BASE_URL")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        graphService = retrofit.create(GraphService.class);
    }

    public void checkInput(String token, final CheckInputListener checkInputListener) {
        Call<Boolean> call = graphService.checkInputToday(token);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    if (response.code() == 401) checkInputListener.onWrongToken();
                    return;
                }

                if (response.body()) checkInputListener.isSet();
                else checkInputListener.isNotSet();
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {

            }
        });
    }

    public void getGraph(String token, final LoadGraphListener loadGraphListener, final int graphType) {
        String typeOfGraph;

        switch (graphType) {
            case GraphModel.WEIGHT_GRAPH:
                typeOfGraph = "weight";
                break;

            case GraphModel.MUSCLE_MATH_GRAPH:
                typeOfGraph = "muscleMass";
                break;

            case GraphModel.BODY_FAT_MASS_GRAPH:
                typeOfGraph = "bodyFatMass";
                break;

            default:
                return;
        }

        Call<List<Graph>> call = graphService.getGraph(token, typeOfGraph);
        call.enqueue(new Callback<List<Graph>>() {
            @Override
            public void onResponse(@NonNull Call<List<Graph>> call, @NonNull Response<List<Graph>> response) {
                if (!response.isSuccessful()) {
                    if (response.code() == 401) loadGraphListener.onWrongToken();
                    return;
                }

                assert response.body() != null;
                if (!response.body().isEmpty()) {
                    loadGraphListener.loadGraph(response.body(), graphType);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Graph>> call, @NonNull Throwable t) {

            }
        });
    }

    public void postGraph(String token, float weight, float muscleMass, float bodyFatMass,
                          EditGraphListener editGraphListener) {
        Call<Void> call = graphService.postGraph(token, weight, muscleMass, bodyFatMass);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    editGraphListener.onSuccess();
                } else {
                    if (response.code() == 401) editGraphListener.onWrongToken();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public void putGraph(String token, float weight, float muscleMass, float bodyFatMass,
                         EditGraphListener editGraphListener) {
        Call<Void> call = graphService.putGraph(token, weight, muscleMass, bodyFatMass);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    editGraphListener.onSuccess();
                } else {
                    if (response.code() == 401) editGraphListener.onWrongToken();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public void deleteGraph(String token, EditGraphListener editGraphListener) {
        Call<Void> call = graphService.deleteGraph(token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    editGraphListener.onSuccess();
                } else {
                    if (response.code() == 401) editGraphListener.onWrongToken();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }
}