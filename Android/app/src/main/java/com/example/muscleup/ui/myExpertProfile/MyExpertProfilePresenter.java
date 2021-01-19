package com.example.muscleup.ui.myExpertProfile;

import com.example.muscleup.model.ExpertModel;
import com.example.muscleup.model.ImageModel;
import com.example.muscleup.model.TokenModel;
import com.example.muscleup.model.UserProfileModel;
import com.example.muscleup.model.callback.LoadImageListener;
import com.example.muscleup.model.callback.LoadMyExpertProfileListener;
import com.example.muscleup.model.callback.LoadTokenListener;
import com.example.muscleup.model.callback.LoadUserInfoListener;
import com.example.muscleup.model.data.ExpertProfile;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;

public class MyExpertProfilePresenter implements MyExpertProfileContract.Presenter {

    public static final int ERROR_MY_EXPERT_PROFILE = 0;
    public static final int ERROR_USER_PROFILE = 1;
    public static final int ERROR_IMAGE = 2;

    private MyExpertProfileContract.View view;
    private ExpertModel expertModel;
    private UserProfileModel userProfileModel;
    private ImageModel imageModel;
    private TokenModel tokenModel;

    public MyExpertProfilePresenter(MyExpertProfileContract.View view) {
        this.view = view;
        expertModel = new ExpertModel();
        userProfileModel = new UserProfileModel();
        imageModel = new ImageModel();
        tokenModel = new TokenModel();
    }

    @Override
    public void getMyExpertProfile(String token) {
        expertModel.getMyExpertProfile("Bearer " + token, new LoadMyExpertProfileListener() {
            @Override
            public void loadMyExpertProfile(ExpertProfile expertProfile) {
                view.setMyExpertProfile(expertProfile);
            }

            @Override
            public void onWrongToken() {
                view.tokenError(ERROR_MY_EXPERT_PROFILE);
            }
        });
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
                view.tokenError(ERROR_USER_PROFILE);
            }
        });
    }

    @Override
    public void getImage(String token, String imageName, int requestType) {
        imageModel.getImage("Bearer " + token, imageName, requestType, new LoadImageListener() {
            @Override
            public void onSuccess(byte[] image, int requestType) {
                if (requestType == ExpertModel.IMAGE_TYPE_PROFILE) view.setProfileImage(image);
                else if (requestType == ExpertModel.IMAGE_TYPE_CERTIFICATE)
                    view.setCertificateImage(image);
            }

            @Override
            public void onWrongToken() {
                view.tokenError(ERROR_IMAGE);
            }
        });
    }

    @Override
    public void tokenRefresh(String refreshToken, int errorType) {
        tokenModel.getNewToken(refreshToken, new LoadTokenListener() {
            @Override
            public void loadToken(Token token) {
                if (errorType == ERROR_MY_EXPERT_PROFILE) view.retryGetMyExpertProfile(token);
                else if (errorType == ERROR_USER_PROFILE) view.retryGetUserProfile(token);
                else if (errorType == ERROR_IMAGE) view.retryGetImage(token);
            }

            @Override
            public void onFail() {
                view.gotoLogin();
            }
        });
    }
}
