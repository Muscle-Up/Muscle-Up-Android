package com.example.muscleup.ui.changeProfile;

import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;

public class EditProfileContract {
    public interface View {
        void setCurProfile(UserProfile userProfile);

        void editSuccess();

        void tokenError(int errorType);

        void retryLoadUserProfile(Token token);

        void retryEditUserProfile(Token token);

        void gotoLogin();
    }

    public interface Presenter {
        void getProfile(String token);

        void editProfile(
                String token, String name, int age, String sex, float height, float weight, byte[] image);

        void tokenRefresh(String refreshToken, int errorType);
    }
}