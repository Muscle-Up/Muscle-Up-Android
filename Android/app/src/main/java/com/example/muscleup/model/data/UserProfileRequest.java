package com.example.muscleup.model.data;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserProfileRequest {
    private RequestBody name;
    private RequestBody age;
    private RequestBody height;
    private RequestBody weight;
    private MultipartBody.Part image;

    public UserProfileRequest(RequestBody name, RequestBody age, RequestBody height, RequestBody weight, MultipartBody.Part image) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.image = image;
    }

    public RequestBody getName() {
        return name;
    }

    public RequestBody getAge() {
        return age;
    }

    public RequestBody getHeight() {
        return height;
    }

    public RequestBody getWeight() {
        return weight;
    }

    public MultipartBody.Part getImage() {
        return image;
    }
}
