package com.example.muscleup.model.callback;

import com.example.muscleup.model.data.UserProfile;

public interface LoadUserInfoListener {
    void loadUserInfo(UserProfile userInfo);

    void onWrongToken();
}
