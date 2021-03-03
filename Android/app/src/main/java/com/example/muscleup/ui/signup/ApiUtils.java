package com.example.muscleup.ui.signup;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://15.165.38.79/";

    public static ApiService getAPIService() {
        return com.example.muscleup.signup.RetrofitClient.getRetrofit(BASE_URL).create(ApiService.class);
    }
}
