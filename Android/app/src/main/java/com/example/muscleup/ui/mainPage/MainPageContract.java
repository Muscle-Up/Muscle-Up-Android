package com.example.muscleup.ui.mainPage;

import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;

public class MainPageContract {
    public interface View {
        void setUserProfile(UserProfile userProfile);

        void setImage(byte[] image);

        void tokenError(int errorType);

        void retryGetUserProfile(Token token);

        void retryGetImage(Token token);

        void gotoLogin();
    }

    public interface Presenter {
        void getUserProfile(String token);

        void getImage(String token, String imageName);

        void tokenRefresh(String refreshToken, int errorType);
    }
}