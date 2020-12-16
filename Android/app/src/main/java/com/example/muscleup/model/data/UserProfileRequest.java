package com.example.muscleup.model.data;

public class UserProfileRequest {
    private String name;
    private int age;
    private String sex;
    private float height;
    private float weight;

    public UserProfileRequest(String name, int age, String sex, float height, float weight) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
    }
}
