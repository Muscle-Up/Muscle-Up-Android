package com.example.muscleup.ui.main;

import com.example.muscleup.model.TokenModel;
import com.example.muscleup.model.callback.LoadTokenListener;
import com.example.muscleup.model.data.Token;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private TokenModel tokenModel;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        tokenModel = new TokenModel();
    }

    @Override
    public void getNewToken(String refreshToken) {
        tokenModel.getNewToken(refreshToken, new LoadTokenListener() {
            @Override
            public void loadToken(Token token) {
                view.setToken(token);
            }

            @Override
            public void onFail() {

            }
        });
    }
}