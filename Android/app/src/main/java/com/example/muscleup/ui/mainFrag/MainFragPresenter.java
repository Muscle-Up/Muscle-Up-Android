package com.example.muscleup.ui.mainFrag;

import com.example.muscleup.model.BodyPostModel;
import com.example.muscleup.model.GraphModel;
import com.example.muscleup.model.TokenModel;
import com.example.muscleup.model.UserProfileModel;
import com.example.muscleup.model.callback.LoadBodyPostImageListener;
import com.example.muscleup.model.callback.LoadBodyPostListener;
import com.example.muscleup.model.callback.LoadGraphListener;
import com.example.muscleup.model.callback.LoadTokenListener;
import com.example.muscleup.model.callback.LoadUserInfoListener;
import com.example.muscleup.model.data.BodyPost;
import com.example.muscleup.model.data.Graph;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;

import java.util.List;

public class MainFragPresenter implements MainFragContract.Presenter {

    public static final int ERROR_GET_GRAPH = 0;
    public static final int ERROR_GET_POST = 1;
    public static final int ERROR_GET_POST_IMAGE = 2;
    public static final int ERROR_GET_USER_PROFILE = 3;

    private GraphModel graphModel;
    private TokenModel tokenModel;
    private BodyPostModel bodyPostModel;
    private UserProfileModel userProfileModel;
    private MainFragContract.View view;

    private LoadGraphListener loadGraphListener = new LoadGraphListener() {
        @Override
        public void loadGraph(List<Graph> graphList, int graphType) {
            switch (graphType) {
                case GraphModel.WEIGHT_GRAPH:
                    view.setWeightGraph(graphList);
                    break;

                case GraphModel.MUSCLE_MATH_GRAPH:
                    view.setMuscleMassGraph(graphList);
                    break;

                case GraphModel.BODY_FAT_MASS_GRAPH:
                    view.setBodyFatMassGraph(graphList);
                    break;

                default:
            }
        }

        @Override
        public void onWrongToken() {
            view.tokenError(GraphModel.ERROR_LOAD_GRAPH);
        }
    };

    public MainFragPresenter(MainFragContract.View view) {
        graphModel = new GraphModel();
        tokenModel = new TokenModel();
        bodyPostModel = new BodyPostModel();
        userProfileModel = new UserProfileModel();
        this.view = view;
    }

    @Override
    public void getGraph(String token) {
        graphModel.getGraph("Bearer " + token, loadGraphListener, GraphModel.MUSCLE_MATH_GRAPH);
        graphModel.getGraph("Bearer " + token, loadGraphListener, GraphModel.BODY_FAT_MASS_GRAPH);
        graphModel.getGraph("Bearer " + token, loadGraphListener, GraphModel.WEIGHT_GRAPH);
    }

    @Override
    public void getPost(String token) {
        bodyPostModel.getBodyPost("Bearer " + token, new LoadBodyPostListener() {
            @Override
            public void onSuccess(List<BodyPost> bodyPostList) {
                view.setPost(bodyPostList.get(0));
            }

            @Override
            public void onWrongToken() {
                view.tokenError(ERROR_GET_POST);
            }
        });
    }

    @Override
    public void getPostImage(String token, String imageName) {
        bodyPostModel.getBodyPostImage("Bearer " + token, imageName, new LoadBodyPostImageListener() {
            @Override
            public void onSuccess(byte[] image) {
                view.setPostImage(image);
            }

            @Override
            public void onWrongToken() {
                view.tokenError(ERROR_GET_POST_IMAGE);
            }
        });
    }

    @Override
    public void getUserProfile(String token) {
        userProfileModel.getUserProfile("Bearer " + token, new LoadUserInfoListener() {
            @Override
            public void loadUserInfo(UserProfile userProfile) {
                view.setUserProfile(userProfile);
            }

            @Override
            public void onWrongToken() {
                view.tokenError(ERROR_GET_USER_PROFILE);
            }
        });
    }

    @Override
    public void tokenRefresh(String refreshToken, int errorType) {
        tokenModel.getNewToken(refreshToken, new LoadTokenListener() {
            @Override
            public void loadToken(Token token) {
                switch (errorType) {
                    case ERROR_GET_GRAPH:
                        view.retryGetGraph(token);
                        break;

                    case ERROR_GET_POST:
                        view.retryGetPost(token);
                        break;

                    case ERROR_GET_POST_IMAGE:
                        view.retryGetPostImage(token);
                        break;

                    case ERROR_GET_USER_PROFILE:
                        view.retryGetUserProfile(token);
                        break;
                }
            }

            @Override
            public void onFail() {
                view.gotoLogin();
            }
        });
    }
}