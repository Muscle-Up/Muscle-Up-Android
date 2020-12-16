package com.example.muscleup.ui.changePassword;

import com.example.muscleup.model.ChangePasswordModel;
import com.example.muscleup.model.TokenModel;
import com.example.muscleup.model.callback.ChangePasswordListener;
import com.example.muscleup.model.callback.LoadTokenListener;
import com.example.muscleup.model.data.Token;

public class ChangePasswordPresenter implements ChangePasswordContract.Presenter {

    private ChangePasswordModel changePasswordModel;
    private TokenModel tokenModel;
    private ChangePasswordContract.View view;

    private LoadTokenListener loadTokenListener = new LoadTokenListener() {
        @Override
        public void loadToken(Token token) {
            view.retryChangePassword(token);
        }

        @Override
        public void onFail() {
            view.gotoLogin();
        }
    };

    public ChangePasswordPresenter(ChangePasswordContract.View view){
        changePasswordModel = new ChangePasswordModel(new ChangePasswordListener() {
            @Override
            public void onSuccess() {
                view.changeSuccess();
            }

            @Override
            public void onWrongToken() {
                view.tokenError();
            }
        });
        this.view = view;
        tokenModel = new TokenModel();
    }

    @Override
    public void changePassword(String token, String password) {
        changePasswordModel.changePassword(token, password);
    }

    @Override
    public void TokenRefresh(String refreshToken) {
        tokenModel.getNewToken(refreshToken, loadTokenListener);
    }
}
