package com.example.muscleup.model.callback;

import com.example.muscleup.model.data.Graph;

import java.util.List;

public interface LoadGraphListener {
    void loadGraph(List<Graph> graphList, int graphType);

    void onWrongToken();
}
