package com.example.muscleup.ui.pose;

import com.example.muscleup.model.data.Pose;
import com.example.muscleup.model.data.Token;

import java.util.List;

public class PoseContract {
    public interface View {
        void setPose(List<Pose> poseList, int position);

        void tokenError();

        void retryAnalyzePose(Token token);

        void gotoLogin();
    }

    public interface Presenter {
        void analyzePose(String token, byte[] pose, int position);

        void tokenRefresh(String refreshToken);
    }
}
