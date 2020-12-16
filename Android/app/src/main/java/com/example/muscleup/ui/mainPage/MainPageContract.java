package com.example.muscleup.ui.mainPage;

import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;

public class MainPageContract {
    public interface View {
        void setUserProfile(UserProfile userProfile);

        void tokenError();

        void retryGetUserProfile(Token token);

        void gotoLogin();
    }

    public interface Presenter {
        void getUserProfile(String token);

        void tokenRefresh(String refreshToken);
    }
}