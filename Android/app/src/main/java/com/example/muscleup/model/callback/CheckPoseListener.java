package com.example.muscleup.model.callback;

import com.example.muscleup.model.data.Pose;

import java.util.List;

public interface CheckPoseListener {
    void onSuccess(List<Pose> poseList, int index);

    void onWrongToken();
}
