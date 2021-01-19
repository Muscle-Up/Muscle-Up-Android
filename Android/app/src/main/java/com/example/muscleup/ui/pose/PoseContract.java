package com.example.muscleup.ui.pose;

import com.example.muscleup.model.data.Pose;
import com.example.muscleup.model.data.Token;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PoseContract {
    public interface View {
        void setPose(List<Pose> poseList, int position);

        void tokenError();

        void retryAnalyzePose(Token token);

        void gotoLogin();
    }

    public interface Presenter {
        void analyzePose(String token, RequestBody pose, int position);

        void tokenRefresh(String refreshToken);
    }
}
