package com.example.muscleup.ui.editProfile;

import com.example.muscleup.model.ImageModel;
import com.example.muscleup.model.TokenModel;
import com.example.muscleup.model.UserProfileModel;
import com.example.muscleup.model.callback.EditUserInfoListener;
import com.example.muscleup.model.callback.LoadImageListener;
import com.example.muscleup.model.callback.LoadTokenListener;
import com.example.muscleup.model.callback.LoadUserInfoListener;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;
import com.example.muscleup.model.data.UserProfileRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfilePresenter implements EditProfileContract.Presenter {

    public static final int ERROR_GET_IMAGE = 101;

    private UserProfileModel userProfileModel;
    private ImageModel imageModel;
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
        imageModel = new ImageModel();
        tokenModel = new TokenModel();
        this.view = view;
    }

    @Override
    public void getProfile(String token) {
        userProfileModel.getUserProfile("Bearer " + token, loadUserInfoListener);
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
    public void editProfile(
            String token, RequestBody name, RequestBody age, RequestBody height, RequestBody weight, MultipartBody.Part image) {
        userProfileModel.editUserProfile("Bearer " + token,
                new UserProfileRequest(name, age, height, weight, image),
                editUserInfoListener);
    }

    @Override
    public void tokenRefresh(String refreshToken, int errorType) {
        tokenModel.getNewToken(refreshToken, new LoadTokenListener() {
            @Override
            public void loadToken(Token token) {
                switch (errorType) {
                    case UserProfileModel.ERROR_LOAD_PROFILE:
                        view.retryLoadUserProfile(token);
                        break;

                    case UserProfileModel.ERROR_EDIT_PROFILE:
                        view.retryEditUserProfile(token);
                        break;

                    case ERROR_GET_IMAGE:
                        view.retryGetImage(token);

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