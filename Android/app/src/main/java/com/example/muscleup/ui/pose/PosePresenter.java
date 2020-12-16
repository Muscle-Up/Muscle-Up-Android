package com.example.muscleup.ui.pose;

import com.example.muscleup.model.PoseModel;
import com.example.muscleup.model.TokenModel;
import com.example.muscleup.model.callback.CheckPoseListener;
import com.example.muscleup.model.callback.LoadTokenListener;
import com.example.muscleup.model.data.Pose;
import com.example.muscleup.model.data.Token;

import java.util.List;

public class PosePresenter implements PoseContract.Presenter {

    private PoseModel poseModel;
    private TokenModel tokenModel;
    private PoseContract.View view;

    private LoadTokenListener loadTokenListener = new LoadTokenListener() {
        @Override
        public void loadToken(Token token) {
            view.retryAnalyzePose(token);
        }

        @Override
        public void onFail() {
            view.gotoLogin();
        }
    };

    public PosePresenter(PoseContract.View view) {
        poseModel = new PoseModel(new CheckPoseListener() {
            @Override
            public void onSuccess(List<Pose> poseList, int position) {
                view.setPose(poseList, position);
            }

            @Override
            public void onWrongToken() {
                view.tokenError();
            }
        });
        tokenModel = new TokenModel();
        this.view = view;
    }

    @Override
    public void analyzePose(String token, byte[] pose, int position) {
        poseModel.checkPose(token, pose, position);
    }

    @Override
    public void tokenRefresh(String refreshToken) {
        tokenModel.getNewToken(refreshToken, loadTokenListener);
    }
}