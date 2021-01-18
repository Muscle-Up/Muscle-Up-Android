package com.example.muscleup.model.data;

public class ChangePasswordRequest {
    private String password;

    public ChangePasswordRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
