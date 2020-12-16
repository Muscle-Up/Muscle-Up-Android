package com.example.muscleup.ui.main;

import com.example.muscleup.model.data.Token;

public class MainContract {
    public interface View {
        void setToken(Token token);
    }

    public interface Presenter {
        void getNewToken(String refreshToken);
    }
}
