package com.example.muscleup.ui.myExpertProfile;

import com.example.muscleup.model.data.ExpertProfile;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;

public class MyExpertProfileContract {
    public interface View {
        void setMyExpertProfile(ExpertProfile expertProfile);

        void setUserProfile(UserProfile userProfile);

        void setProfileImage(byte[] image);

        void setCertificateImage(byte[] image);

        void tokenError(int errorType);

        void retryGetMyExpertProfile(Token token);

        void retryGetUserProfile(Token token);

        void retryGetImage(Token token);

        void gotoLogin();
    }

    public interface Presenter {
        void getMyExpertProfile(String token);

        void getUserProfile(String token);

        void getImage(String token, String imageName, int requestType);

        void tokenRefresh(String refreshToken, int errorType);
    }
}
