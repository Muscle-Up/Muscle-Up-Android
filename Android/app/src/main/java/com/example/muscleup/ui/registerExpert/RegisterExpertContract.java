package com.example.muscleup.ui.registerExpert;

import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;

public class RegisterExpertContract {
    public interface View {
        void registerSuccess();

        void setUserProfile(UserProfile userProfile);

        void tokenError(int errorType);

        void retryRegisterExpert(Token token);

        void retryGetUserProfile(Token token);

        void gotoLogin();
    }

    public interface Presenter {
        void registerExpert(
                String token, String intro, String name, String date, byte[] certificateImg, byte[] profileImg);

        void getUserProfile(String token);

        void tokenRefresh(String token, int errorType);
    }
}