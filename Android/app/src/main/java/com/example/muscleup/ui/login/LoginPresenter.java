package com.example.muscleup.ui.login;

import com.example.muscleup.model.TokenModel;
import com.example.muscleup.model.callback.LoadTokenListener;
import com.example.muscleup.model.data.Login;
import com.example.muscleup.model.data.Token;

public class LoginPresenter implements LoginContract.Presenter {

    private TokenModel tokenModel;
    private LoginContract.View view;

    private LoadTokenListener loadTokenListener = new LoadTokenListener() {
        @Override
        public void loadToken(Token token) {
            view.setToken(token);
        }

        @Override
        public void onFail() {
            view.loginFail();
        }
    };

    public LoginPresenter(LoginContract.View view) {
        tokenModel = new TokenModel();
        this.view = view;
    }

    @Override
    public void login(Login login) {
        tokenModel.login(login, loadTokenListener);
    }
}
