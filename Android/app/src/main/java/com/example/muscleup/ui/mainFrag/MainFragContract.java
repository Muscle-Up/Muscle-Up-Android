package com.example.muscleup.ui.mainFrag;

import com.example.muscleup.model.data.BodyPost;
import com.example.muscleup.model.data.Graph;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;

import java.util.List;

public class MainFragContract {
    public interface View {
        void setWeightGraph(List<Graph> weightGraphList);

        void setBodyFatMassGraph(List<Graph> bodyFatMassGraphList);

        void setMuscleMassGraph(List<Graph> muscleMassGraphList);

        void setPost(BodyPost bodyPost);

        void setPostImage(byte[] image);

        void setUserProfile(UserProfile userProfile);

        void setImage(byte[] image);

        void tokenError(int errorType);

        void retryGetGraph(Token token);

        void retryGetPost(Token token);

        void retryGetPostImage(Token token);

        void retryGetUserProfile(Token token);

        void retryGetImage(Token token);

        void gotoLogin();
    }

    public interface Presenter {
        void getGraph(String token);

        void getPost(String token);

        void getPostImage(String token, String imageName);

        void getUserProfile(String token);

        void getImage(String token, String imageName);

        void tokenRefresh(String refreshToken, int errorType);
    }
}