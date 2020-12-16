package com.example.muscleup.ui.changePassword;

import com.example.muscleup.model.data.Token;

public class ChangePasswordContract {
    public interface View {
        void changeSuccess();

        void tokenError();

        void retryChangePassword(Token token);

        void gotoLogin();
    }

    public interface Presenter {
        void changePassword(String token, String password);

        void TokenRefresh(String refreshToken);
    }
}
