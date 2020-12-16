package com.example.muscleup.ui.login;

import com.example.muscleup.model.data.Login;
import com.example.muscleup.model.data.Token;

public class LoginContract {
    public interface View {
        void loginFail();

        void setToken(Token token);
    }

    public interface Presenter {
        void login(Login login);
    }
}
