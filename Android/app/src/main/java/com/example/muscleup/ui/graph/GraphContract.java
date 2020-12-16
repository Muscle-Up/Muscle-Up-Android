package com.example.muscleup.ui.graph;

import com.example.muscleup.model.data.Graph;
import com.example.muscleup.model.data.Token;

import java.util.List;

public class GraphContract {
    public interface View {
        void setInputUI();

        void setFixUI();

        void setWeightGraph(List<Graph> weightGraphList);

        void setBodyFatMassGraph(List<Graph> bodyFatMassGraphList);

        void setMuscleMassGraph(List<Graph> muscleMassGraphList);

        void refreshGraph();

        void tokenError(int errorType);

        void retryCheckInput(Token token);

        void retryGetGraph(Token token);

        void retryInputGraph(Token token);

        void retryFixGraph(Token token);

        void retryDeleteGraph(Token token);

        void gotoLogin();
    }

    public interface Presenter {
        void checkInput(String token);

        void getGraph(String token);

        void inputGraph(String token, float weight, float muscleMass, float bodyFatMass);

        void fixGraph(String token, float weight, float muscleMass, float bodyFatMass);

        void deleteGraph(String token);

        void tokenRefresh(String refreshToken, int errorType);
    }
}