package com.example.muscleup.ui.registerExpert;

import com.example.muscleup.model.ExpertModel;
import com.example.muscleup.model.TokenModel;
import com.example.muscleup.model.UserProfileModel;
import com.example.muscleup.model.callback.LoadTokenListener;
import com.example.muscleup.model.callback.LoadUserInfoListener;
import com.example.muscleup.model.callback.RegisterExpertListener;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterExpertPresenter implements RegisterExpertContract.Presenter {

    private RegisterExpertContract.View view;
    private TokenModel tokenModel;
    private ExpertModel expertModel;

    public RegisterExpertPresenter(RegisterExpertContract.View view) {
        this.view = view;
        tokenModel = new TokenModel();
        expertModel = new ExpertModel();
    }

    @Override
    public void registerExpert(String token, RequestBody intro, RequestBody name, RequestBody date, MultipartBody.Part certificateImg) {
        expertModel.registerExpert("Bearer " + token, intro, name, date, certificateImg, new RegisterExpertListener() {
            @Override
            public void onSuccess() {
                view.registerSuccess();
            }

            @Override
            public void onWrongToken() {
                view.tokenError();
            }
        });
    }


    @Override
    public void tokenRefresh(String token) {
        tokenModel.getNewToken(token, new LoadTokenListener() {
            @Override
            public void loadToken(Token token) {
                view.retryRegisterExpert(token);
            }

            @Override
            public void onFail() {
                view.gotoLogin();
            }
        });
    }
}
