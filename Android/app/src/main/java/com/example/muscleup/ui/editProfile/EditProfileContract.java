package com.example.muscleup.ui.editProfile;

import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileContract {
    public interface View {
        void setCurProfile(UserProfile userProfile);

        void setImage(byte[] image);

        void editSuccess();

        void tokenError(int errorType);

        void retryLoadUserProfile(Token token);

        void retryGetImage(Token token);

        void retryEditUserProfile(Token token);

        void gotoLogin();
    }

    public interface Presenter {
        void getProfile(String token);

        void getImage(String token, String imageName);

        void editProfile(
                String token, RequestBody name, RequestBody age, RequestBody height, RequestBody weight, MultipartBody.Part image);

        void tokenRefresh(String refreshToken, int errorType);
    }
}