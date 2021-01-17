package com.example.muscleup.ui.editProfile;

import android.net.Uri;

import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
                String token, RequestBody name, RequestBody age, RequestBody height, RequestBody weight, MultipartBody.Part image);

        void tokenRefresh(String refreshToken, int errorType);
    }
}