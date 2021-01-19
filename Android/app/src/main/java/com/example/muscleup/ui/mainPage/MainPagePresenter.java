package com.example.muscleup.ui.mainPage;

import com.example.muscleup.model.ImageModel;
import com.example.muscleup.model.TokenModel;
import com.example.muscleup.model.UserProfileModel;
import com.example.muscleup.model.callback.LoadImageListener;
import com.example.muscleup.model.callback.LoadTokenListener;
import com.example.muscleup.model.callback.LoadUserInfoListener;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;

public class MainPagePresenter implements MainPageContract.Presenter {

    public static final int ERROR_GET_PROFILE = 0;
    public static final int ERROR_GET_IMAGE = 1;

    private UserProfileModel userProfileModel;
    private ImageModel imageModel;
    private TokenModel tokenModel;
    private MainPageContract.View view;

    public MainPagePresenter(MainPageContract.View view) {
        userProfileModel = new UserProfileModel();
        imageModel = new ImageModel();
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
                view.tokenError(ERROR_GET_PROFILE);
            }
        });
    }

    @Override
    public void getImage(String token, String imageName) {
        imageModel.getImage("Bearer " + token, imageName, 0, new LoadImageListener() {
            @Override
            public void onSuccess(byte[] image, int requestType) {
                view.setImage(image);
            }

            @Override
            public void onWrongToken() {
                view.tokenError(ERROR_GET_IMAGE);
            }
        });
    }

    @Override
    public void tokenRefresh(String refreshToken, int errorType) {
        tokenModel.getNewToken(refreshToken, new LoadTokenListener() {
            @Override
            public void loadToken(Token token) {
                if (errorType == ERROR_GET_PROFILE) view.retryGetUserProfile(token);
                else if (errorType == ERROR_GET_IMAGE) view.retryGetImage(token);
            }

            @Override
            public void onFail() {
                view.gotoLogin();
            }
        });
    }
}
