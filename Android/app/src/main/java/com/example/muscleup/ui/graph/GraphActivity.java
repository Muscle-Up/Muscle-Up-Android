package com.example.muscleup.ui.graph;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.muscleup.R;
import com.example.muscleup.adapter.SpinnerAdapter;
import com.example.muscleup.databinding.ActivityGraphBinding;
import com.example.muscleup.dialog.CustomDialog;
import com.example.muscleup.model.callback.CustomDialogListener;
import com.example.muscleup.model.data.Graph;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.ui.main.MainActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity implements GraphContract.View {

    private static final int GRAPH_WEIGHT = 0;
    private static final int GRAPH_BODY_FAT = 1;
    private static final int GRAPH_MUSCLE = 2;

    private List<Entry> weightGraphList = new ArrayList<>();
    private List<Entry> bodyFatMassGraphList = new ArrayList<>();
    private List<Entry> muscleMassGraphList = new ArrayList<>();

    private ActivityGraphBinding binding;
    private GraphContract.Presenter presenter;

    private float inputWeight;
    private float inputMuscleMass;
    private float inputBodyFatMass;

    private float fixWeight;
    private float fixMuscleMass;
    private float fixBodyFatMass;

    private int lastIndexId;
    private int curGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_graph);

        ArrayList<String> graphList = new ArrayList<>();
        graphList.add("근육량");
        graphList.add("체지방량");
        graphList.add("몸무게");
        binding.graphSpinner.setAdapter(new SpinnerAdapter(this, graphList));

        binding.graphSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        setGraph(binding.graph, muscleMassGraphList, "근육량");
                        break;

                    case 1:
                        setGraph(binding.graph, bodyFatMassGraphList, "체지방량");
                        break;

                    case 2:
                        setGraph(binding.graph, weightGraphList, "몸무게");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        binding.graphBtnBack.setOnClickListener(view -> finish());

        binding.graphBtnInput.setOnClickListener(view -> {
            if (binding.graphEtInputWeight.getText().length() < 1) return;
            if (binding.graphEtInputBodyFatMass.getText().length() < 1) return;
            if (binding.graphEtInputMuscleMass.getText().length() < 1) return;

            inputWeight = Float.parseFloat(binding.graphEtInputWeight.getText().toString());
            inputBodyFatMass = Float.parseFloat(binding.graphEtInputBodyFatMass.getText().toString());
            inputMuscleMass = Float.parseFloat(binding.graphEtInputMuscleMass.getText().toString());

            presenter.inputGraph(getToken(), inputWeight, inputMuscleMass, inputBodyFatMass);
        });

        binding.graphBtnFix.setOnClickListener(view -> {
            if (binding.graphEtFixWeight.getText().length() < 1) return;
            if (binding.graphEtFixBodyFatMass.getText().length() < 1) return;
            if (binding.graphEtFixMuscleMass.getText().length() < 1) return;

            fixWeight = Float.parseFloat(binding.graphEtFixWeight.getText().toString());
            fixBodyFatMass = Float.parseFloat(binding.graphEtFixBodyFatMass.getText().toString());
            fixMuscleMass = Float.parseFloat(binding.graphEtFixMuscleMass.getText().toString());

            presenter.fixGraph(getToken(), fixWeight, fixMuscleMass, fixBodyFatMass, lastIndexId);
        });

        binding.graphBtnDelete.setOnClickListener(view -> {
            CustomDialog customDialog = new CustomDialog("입력하신 내용을 삭제하시겠습니까?", this, new CustomDialogListener() {
                @Override
                public void onClickOk() {
                    presenter.deleteGraph(getToken(), lastIndexId);
                }

                @Override
                public void onClickNo() {

                }
            });
            customDialog.callFunction();
        });

        presenter = new GraphPresenter(this);
        presenter.checkInput(getToken());
        presenter.getGraph(getToken());

        binding.graphSpinner.setSelection(0);
        curGraph = GRAPH_MUSCLE;
        setGraph(binding.graph, muscleMassGraphList, "근육량");
    }

    @Override
    public void setInputUI() {
        binding.clFixGraph.setVisibility(View.GONE);
        binding.graphProgress.setVisibility(View.GONE);
        binding.tvInputStatus.setText("오늘 신체 수치를 입력하지 않았습니다");
        binding.clInputGraph.setVisibility(View.VISIBLE);
    }

    @Override
    public void setFixUI() {
        binding.clInputGraph.setVisibility(View.GONE);
        binding.graphProgress.setVisibility(View.GONE);
        binding.tvInputStatus.setText("오늘 신체 수치를 입력하셨습니다");
        binding.clFixGraph.setVisibility(View.VISIBLE);
    }

    @Override
    public void setWeightGraph(List<Graph> weightGraphList) {
        this.weightGraphList.clear();
        for (Graph graph : weightGraphList) {
            this.weightGraphList.add(new Entry(graph.getId(), graph.getValue()));
        }
        if(curGraph == GRAPH_WEIGHT) setGraph(binding.graph, this.weightGraphList, "몸무게");
        lastIndexId = weightGraphList.get(weightGraphList.size() - 1).getId();
    }

    @Override
    public void setBodyFatMassGraph(List<Graph> bodyFatMassGraphList) {
        this.bodyFatMassGraphList.clear();
        for (Graph graph : bodyFatMassGraphList) {
            this.bodyFatMassGraphList.add(new Entry(graph.getId(), graph.getValue()));
            Log.d("GraphActivity", "setBodyFatMassGraph: " + graph.getId() + " " + graph.getValue());
        }
        if(curGraph == GRAPH_BODY_FAT) setGraph(binding.graph, this.bodyFatMassGraphList, "체지방량");
        lastIndexId = bodyFatMassGraphList.get(bodyFatMassGraphList.size() - 1).getId();
    }

    @Override
    public void setMuscleMassGraph(List<Graph> muscleMassGraphList) {
        this.muscleMassGraphList.clear();
        for (Graph graph : muscleMassGraphList) {
            this.muscleMassGraphList.add(new Entry(graph.getId(), graph.getValue()));
        }
        if(curGraph == GRAPH_MUSCLE) setGraph(binding.graph, this.muscleMassGraphList, "근육량");
        lastIndexId = muscleMassGraphList.get(muscleMassGraphList.size() - 1).getId();
    }

    @Override
    public void refreshGraph() {
        presenter.checkInput(getToken());
        presenter.getGraph(getToken());
    }

    @Override
    public void tokenError(int errorType) {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        presenter.tokenRefresh(sharedPreferences.getString("RefreshToken", "null"), errorType);
    }

    @Override
    public void retryCheckInput(Token token) {
        setNewToken(token);
        presenter.checkInput(token.getAccessToken());
    }

    @Override
    public void retryGetGraph(Token token) {
        setNewToken(token);
        presenter.checkInput(token.getAccessToken());
    }

    @Override
    public void retryInputGraph(Token token) {
        setNewToken(token);
        presenter.inputGraph(token.getAccessToken(), inputWeight, inputMuscleMass, inputBodyFatMass);
    }

    @Override
    public void retryFixGraph(Token token) {
        setNewToken(token);
        presenter.fixGraph(token.getAccessToken(), fixWeight, fixMuscleMass, fixBodyFatMass, lastIndexId);
    }

    @Override
    public void retryDeleteGraph(Token token) {
        setNewToken(token);
        presenter.deleteGraph(token.getAccessToken(), lastIndexId);
    }

    @Override
    public void gotoLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        return sharedPreferences.getString("accessToken", "");
    }

    private void setNewToken(Token token) {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("AccessToken", token.getAccessToken());
        editor.putString("RefreshToken", token.getRefreshToken());
        editor.apply();
    }

    private void setGraph(LineChart lineChart, List<Entry> list, String label) {
        LineDataSet lineDataSet = new LineDataSet(list, label);
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(false);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        lineChart.setDescription(description);
        lineChart.invalidate();
    }
}