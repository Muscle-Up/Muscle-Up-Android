package com.example.muscleup.ui.registerExpert;

import com.example.muscleup.model.data.Token;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterExpertContract {
    public interface View {
        void registerSuccess();

        void tokenError();

        void retryRegisterExpert(Token token);

        void gotoLogin();
    }

    public interface Presenter {
        void registerExpert(
                String token, RequestBody intro, RequestBody name, RequestBody date, MultipartBody.Part certificateImg);

        void tokenRefresh(String token);
    }
}