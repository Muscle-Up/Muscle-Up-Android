package com.example.muscleup.model.callback;

import com.example.muscleup.model.data.BodyPost;

import java.util.List;

public interface LoadBodyPostListener {
    void onSuccess(List<BodyPost> bodyPostList);

    void onWrongToken();
}
