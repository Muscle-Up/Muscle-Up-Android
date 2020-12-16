package com.example.muscleup.ui.changeProfile;

import com.example.muscleup.model.TokenModel;
import com.example.muscleup.model.UserProfileModel;
import com.example.muscleup.model.callback.EditUserInfoListener;
import com.example.muscleup.model.callback.LoadTokenListener;
import com.example.muscleup.model.callback.LoadUserInfoListener;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;
import com.example.muscleup.model.data.UserProfileRequest;

public class EditProfilePresenter implements EditProfileContract.Presenter {

    private UserProfileModel userProfileModel;
    private TokenModel tokenModel;
    private EditProfileContract.View view;

    private LoadUserInfoListener loadUserInfoListener = new LoadUserInfoListener() {
        @Override
        public void loadUserInfo(UserProfile userInfo) {
            view.setCurProfile(userInfo);
        }

        @Override
        public void onWrongToken() {
            view.tokenError(UserProfileModel.ERROR_LOAD_PROFILE);
        }
    };

    private EditUserInfoListener editUserInfoListener = new EditUserInfoListener() {
        @Override
        public void onSuccess() {
            view.editSuccess();
        }

        @Override
        public void onWrongToken() {
            view.tokenError(UserProfileModel.ERROR_EDIT_PROFILE);
        }
    };

    public EditProfilePresenter(EditProfileContract.View view) {
        userProfileModel = new UserProfileModel();
        tokenModel = new TokenModel();
        this.view = view;
    }

    @Override
    public void getProfile(String token) {
        userProfileModel.getUserProfile(token, loadUserInfoListener);
    }

    @Override
    public void editProfile(
            String token, String name, int age, String sex, float height, float weight, byte[] image) {
        userProfileModel.editUserProfile(token,
                new UserProfileRequest(name, age, sex, height, weight),
                image, editUserInfoListener);
    }

    @Override
    public void tokenRefresh(String refreshToken, int errorType) {
        tokenModel.getNewToken(refreshToken, new LoadTokenListener() {
            @Override
            public void loadToken(Token token) {
                switch (errorType){
                    case UserProfileModel.ERROR_LOAD_PROFILE:
                        view.retryLoadUserProfile(token);
                        break;

                    case UserProfileModel.ERROR_EDIT_PROFILE:
                        view.retryEditUserProfile(token);
                        break;

                    default:
                }
            }

            @Override
            public void onFail() {
                view.gotoLogin();
            }
        });
    }
}