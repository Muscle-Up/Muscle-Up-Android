package com.example.muscleup.ui.graph;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity implements GraphContract.View {

    private List<Entry> weightGraphList = new ArrayList<>();
    private List<Entry> bodyFatMassGraphList = new ArrayList<>();
    private List<Entry> muscleMassGraphList = new ArrayList<>();

    private LineData weightData = new LineData();
    private LineData bodyFatMassData = new LineData();
    private LineData muscleMassData = new LineData();

    private ActivityGraphBinding binding;
    private GraphContract.Presenter presenter;

    private float inputWeight;
    private float inputMuscleMass;
    private float inputBodyFatMass;

    private float fixWeight;
    private float fixMuscleMass;
    private float fixBodyFatMass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_graph);

        LineDataSet weightDataSet = new LineDataSet(weightGraphList, "몸무게");
        LineDataSet bodyFatMassDataSet = new LineDataSet(bodyFatMassGraphList, "체지방량");
        LineDataSet muscleMassDataSet = new LineDataSet(muscleMassGraphList, "근육량");

        weightData.addDataSet(weightDataSet);
        bodyFatMassData.addDataSet(bodyFatMassDataSet);
        muscleMassData.addDataSet(muscleMassDataSet);

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
                        binding.graph.setData(muscleMassData);
                        binding.graph.invalidate();
                        break;

                    case 1:
                        binding.graph.setData(bodyFatMassData);
                        binding.graph.invalidate();
                        break;

                    case 2:
                        binding.graph.setData(weightData);
                        binding.graph.invalidate();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        binding.graphSpinner.setSelection(0);

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

            presenter.fixGraph(getToken(), fixWeight, fixMuscleMass, fixBodyFatMass);
        });

        binding.graphBtnDelete.setOnClickListener(view -> {
            CustomDialog customDialog = new CustomDialog("입력하신 내용을 삭제하시겠습니까?", this, new CustomDialogListener() {
                @Override
                public void onClickOk() {
                    presenter.deleteGraph(getToken());
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
        for (Graph graph : weightGraphList) {
            this.weightGraphList.add(new Entry(graph.getValue(), graph.getId()));
        }

        binding.graph.notifyDataSetChanged();
        binding.graph.invalidate();
    }

    @Override
    public void setBodyFatMassGraph(List<Graph> bodyFatMassGraphList) {
        for (Graph graph : bodyFatMassGraphList) {
            this.bodyFatMassGraphList.add(new Entry(graph.getValue(), graph.getId()));
        }

        binding.graph.notifyDataSetChanged();
        binding.graph.invalidate();
    }

    @Override
    public void setMuscleMassGraph(List<Graph> muscleMassGraphList) {
        for (Graph graph : muscleMassGraphList) {
            this.muscleMassGraphList.add(new Entry(graph.getValue(), graph.getId()));
        }

        binding.graph.notifyDataSetChanged();
        binding.graph.invalidate();
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
        presenter.fixGraph(token.getAccessToken(), fixWeight, fixMuscleMass, fixBodyFatMass);
    }

    @Override
    public void retryDeleteGraph(Token token) {
        setNewToken(token);

        presenter.deleteGraph(token.getAccessToken());
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
}