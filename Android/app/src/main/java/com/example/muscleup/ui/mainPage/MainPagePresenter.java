package com.example.muscleup.ui.mainPage;

import com.example.muscleup.model.TokenModel;
import com.example.muscleup.model.UserProfileModel;
import com.example.muscleup.model.callback.LoadTokenListener;
import com.example.muscleup.model.callback.LoadUserInfoListener;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;

public class MainPagePresenter implements MainPageContract.Presenter {

    private UserProfileModel userProfileModel;
    private TokenModel tokenModel;
    private MainPageContract.View view;

    public MainPagePresenter(MainPageContract.View view) {
        userProfileModel = new UserProfileModel();
        tokenModel = new TokenModel();
        this.view = view;
    }

    @Override
    public void getUserProfile(String token) {
        userProfileModel.getUserProfile("Bearer " + token, new LoadUserInfoListener() {
            @Override
            public void loadUserInfo(UserProfile userInfo) {
                view.setUserProfile(userInfo);
            }

            @Override
            public void onWrongToken() {
                view.tokenError();
            }
        });
    }

    @Override
    public void tokenRefresh(String refreshToken) {
        tokenModel.getNewToken("Bearer " + refreshToken, new LoadTokenListener() {
            @Override
            public void loadToken(Token token) {
                view.retryGetUserProfile(token);
            }

            @Override
            public void onFail() {
                view.gotoLogin();
            }
        });
    }
}
