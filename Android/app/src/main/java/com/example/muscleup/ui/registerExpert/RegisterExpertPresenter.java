package com.example.muscleup.ui.registerExpert;

import com.example.muscleup.model.ExpertModel;
import com.example.muscleup.model.TokenModel;
import com.example.muscleup.model.UserProfileModel;
import com.example.muscleup.model.callback.LoadTokenListener;
import com.example.muscleup.model.callback.LoadUserInfoListener;
import com.example.muscleup.model.callback.RegisterExpertListener;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;

public class RegisterExpertPresenter implements RegisterExpertContract.Presenter {

    private RegisterExpertContract.View view;
    private TokenModel tokenModel;
    private ExpertModel expertModel;
    private UserProfileModel userProfileModel;

    public static final int ERROR_REGISTER_EXPERT = 0;
    public static final int ERROR_GET_PROFILE = 1;

    public RegisterExpertPresenter(RegisterExpertContract.View view) {
        this.view = view;
        tokenModel = new TokenModel();
        expertModel = new ExpertModel();
        userProfileModel = new UserProfileModel();
    }

    @Override
    public void registerExpert(String token, String intro, String name, String date, byte[] certificateImg, byte[] profileImg) {
        expertModel.registerExpert(token, intro, name, date, certificateImg, profileImg, new RegisterExpertListener() {
            @Override
            public void onSuccess() {
                view.registerSuccess();
            }

            @Override
            public void onWrongToken() {
                view.tokenError(ERROR_REGISTER_EXPERT);
            }
        });
    }

    @Override
    public void getUserProfile(String token) {
        userProfileModel.getUserProfile(token, new LoadUserInfoListener() {
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
    public void tokenRefresh(String token, int errorType) {
        tokenModel.getNewToken(token, new LoadTokenListener() {
            @Override
            public void loadToken(Token token) {
                if(errorType == ERROR_REGISTER_EXPERT) view.retryRegisterExpert(token);
                else if(errorType == ERROR_GET_PROFILE) view.retryGetUserProfile(token);
            }

            @Override
            public void onFail() {
                view.gotoLogin();
            }
        });
    }
}
