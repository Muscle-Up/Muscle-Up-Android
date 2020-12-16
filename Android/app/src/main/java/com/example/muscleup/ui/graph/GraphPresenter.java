package com.example.muscleup.ui.graph;

import com.example.muscleup.model.GraphModel;
import com.example.muscleup.model.TokenModel;
import com.example.muscleup.model.callback.CheckInputListener;
import com.example.muscleup.model.callback.LoadGraphListener;
import com.example.muscleup.model.callback.LoadTokenListener;
import com.example.muscleup.model.callback.EditGraphListener;
import com.example.muscleup.model.data.Graph;
import com.example.muscleup.model.data.Token;

import java.util.List;

public class GraphPresenter implements GraphContract.Presenter {

    private GraphModel graphModel;
    private TokenModel tokenModel;
    private GraphContract.View view;

    private CheckInputListener checkInputListener = new CheckInputListener() {
        @Override
        public void isSet() {
            view.setFixUI();
        }

        @Override
        public void isNotSet() {
            view.setInputUI();
        }

        @Override
        public void onWrongToken() {

        }
    };

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

    public GraphPresenter(GraphContract.View view) {
        graphModel = new GraphModel();
        tokenModel = new TokenModel();
        this.view = view;
    }

    @Override
    public void checkInput(String token) {
        graphModel.checkInput(token, checkInputListener);
    }

    @Override
    public void getGraph(String token) {
        graphModel.getGraph(token, loadGraphListener, GraphModel.MUSCLE_MATH_GRAPH);
        graphModel.getGraph(token, loadGraphListener, GraphModel.BODY_FAT_MASS_GRAPH);
        graphModel.getGraph(token, loadGraphListener, GraphModel.WEIGHT_GRAPH);
    }

    @Override
    public void inputGraph(String token, float weight, float muscleMass, float bodyFatMass) {
        graphModel.postGraph(token, weight, muscleMass, bodyFatMass, new EditGraphListener() {
            @Override
            public void onSuccess() {
                view.refreshGraph();
            }

            @Override
            public void onWrongToken() {
                view.tokenError(GraphModel.ERROR_POST_GRAPH);
            }
        });
    }

    @Override
    public void fixGraph(String token, float weight, float muscleMass, float bodyFatMass) {
        graphModel.putGraph(token, weight, muscleMass, bodyFatMass, new EditGraphListener() {
            @Override
            public void onSuccess() {
                view.refreshGraph();
            }

            @Override
            public void onWrongToken() {
                view.tokenError(GraphModel.ERROR_PUT_GRAPH);
            }
        });
    }

    @Override
    public void deleteGraph(String token) {
        graphModel.deleteGraph(token, new EditGraphListener() {
            @Override
            public void onSuccess() {
                view.refreshGraph();
            }

            @Override
            public void onWrongToken() {
                view.tokenError(GraphModel.ERROR_DELETE_GRAPH);
            }
        });
    }

    @Override
    public void tokenRefresh(String refreshToken, int errorType) {
        tokenModel.getNewToken(refreshToken, new LoadTokenListener() {
            @Override
            public void loadToken(Token token) {
                switch (errorType) {
                    case GraphModel.ERROR_CHECK_INPUT:
                        view.retryCheckInput(token);
                        break;

                    case GraphModel.ERROR_LOAD_GRAPH:
                        view.retryGetGraph(token);
                        break;

                    case GraphModel.ERROR_POST_GRAPH:
                        view.retryInputGraph(token);
                        break;

                    case GraphModel.ERROR_PUT_GRAPH:
                        view.retryFixGraph(token);
                        break;

                    case GraphModel.ERROR_DELETE_GRAPH:
                        view.retryDeleteGraph(token);
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
