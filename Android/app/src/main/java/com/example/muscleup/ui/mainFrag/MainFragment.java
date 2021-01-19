package com.example.muscleup.ui.mainFrag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.muscleup.R;
import com.example.muscleup.adapter.SpinnerAdapter;
import com.example.muscleup.databinding.FragmentMainBinding;
import com.example.muscleup.model.data.BodyPost;
import com.example.muscleup.model.data.Graph;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;
import com.example.muscleup.ui.graph.GraphActivity;
import com.example.muscleup.ui.mainPage.MainPageActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainFragment extends Fragment implements MainFragContract.View {

    private List<Entry> weightGraphList = new ArrayList<>();
    private List<Entry> bodyFatMassGraphList = new ArrayList<>();
    private List<Entry> muscleMassGraphList = new ArrayList<>();

    private FragmentMainBinding binding;
    private MainFragContract.Presenter presenter;

    private String bodyPostImageName;
    private String profileImageName;
    private int curGraph;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        ArrayList<String> graphList = new ArrayList<>();
        graphList.add("근육량");
        graphList.add("체지방량");
        graphList.add("몸무게");
        binding.mainFragSpinner.setAdapter(new SpinnerAdapter(
                Objects.requireNonNull(getActivity()).getApplicationContext(), graphList));

        binding.mainFragSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        curGraph = GraphActivity.GRAPH_MUSCLE;
                        setGraph(binding.mainFragGraph, muscleMassGraphList, "근육량");
                        break;

                    case 1:
                        curGraph = GraphActivity.GRAPH_BODY_FAT;
                        setGraph(binding.mainFragGraph, bodyFatMassGraphList, "체지방량");
                        break;

                    case 2:
                        curGraph = GraphActivity.GRAPH_WEIGHT;
                        setGraph(binding.mainFragGraph, weightGraphList, "몸무게");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        binding.mainFragBtnDrawer.setOnClickListener(
                view -> ((MainPageActivity) getActivity()).openDrawer());

        binding.mainFragGraph.setOnClickListener(
                view -> ((MainPageActivity) getActivity()).startGraphActivity());

        binding.mainFragClPost.setOnClickListener(
                view -> ((MainPageActivity) getActivity()).startBodyPostActivity());

        presenter = new MainFragPresenter(this);
        presenter.getGraph(((MainPageActivity) Objects.requireNonNull(getActivity())).getToken());
        presenter.getPost(((MainPageActivity) getActivity()).getToken());
        presenter.getUserProfile(((MainPageActivity) getActivity()).getToken());

        binding.mainFragSpinner.setSelection(0);

        return binding.getRoot();
    }

    @Override
    public void setWeightGraph(List<Graph> weightGraphList) {
        this.weightGraphList.clear();
        for (Graph graph : weightGraphList) {
            this.weightGraphList.add(new Entry(graph.getId(), graph.getValue()));
        }
        if (curGraph == GraphActivity.GRAPH_WEIGHT)
            setGraph(binding.mainFragGraph, this.weightGraphList, "몸무게");
    }

    @Override
    public void setBodyFatMassGraph(List<Graph> bodyFatMassGraphList) {
        this.bodyFatMassGraphList.clear();
        for (Graph graph : bodyFatMassGraphList) {
            this.bodyFatMassGraphList.add(new Entry(graph.getId(), graph.getValue()));
        }
        if (curGraph == GraphActivity.GRAPH_BODY_FAT)
            setGraph(binding.mainFragGraph, this.bodyFatMassGraphList, "체지방량");
    }

    @Override
    public void setMuscleMassGraph(List<Graph> muscleMassGraphList) {
        this.muscleMassGraphList.clear();
        for (Graph graph : muscleMassGraphList) {
            this.muscleMassGraphList.add(new Entry(graph.getId(), graph.getValue()));
        }
        if (curGraph == GraphActivity.GRAPH_MUSCLE)
            setGraph(binding.mainFragGraph, this.muscleMassGraphList, "근육량");
    }

    @Override
    public void setPost(BodyPost bodyPost) {
        binding.mainFragTvPostName.setText(bodyPost.getTitle());
        binding.mainFragTvPostDate.setText(bodyPost.getWrite_at());
        binding.mainFragTvPostContent.setText(bodyPost.getContent());
        bodyPostImageName = bodyPost.getImageName();
        presenter.getPostImage(
                ((MainPageActivity) Objects.requireNonNull(getActivity())).getToken(), bodyPostImageName);
    }

    @Override
    public void setPostImage(byte[] image) {
        Bitmap postImage = BitmapFactory.decodeByteArray(image, 0, image.length);
        binding.mainFragIvPostImage.setImageBitmap(postImage);
    }

    @Override
    public void setUserProfile(UserProfile userProfile) {
        profileImageName = userProfile.getName();
        presenter.getImage(
                ((MainPageActivity) Objects.requireNonNull(getActivity())).getToken(), profileImageName);
        setGreetMessage(userProfile.getName());
    }

    @Override
    public void setImage(byte[] image) {
        if (image != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            binding.mainPageIvProfile.setImageBitmap(bitmap);
        }
    }

    @Override
    public void tokenError(int errorType) {
        presenter.tokenRefresh(
                ((MainPageActivity) Objects.requireNonNull(getActivity())).getRefreshToken(), errorType);
    }

    @Override
    public void retryGetGraph(Token token) {
        ((MainPageActivity) Objects.requireNonNull(getActivity())).setNewToken(token);
        presenter.getGraph(token.getAccessToken());
    }

    @Override
    public void retryGetPost(Token token) {
        ((MainPageActivity) Objects.requireNonNull(getActivity())).setNewToken(token);
        presenter.getPost(token.getAccessToken());
    }

    @Override
    public void retryGetPostImage(Token token) {
        ((MainPageActivity) Objects.requireNonNull(getActivity())).setNewToken(token);
        presenter.getPostImage(token.getAccessToken(), bodyPostImageName);
    }

    @Override
    public void retryGetUserProfile(Token token) {
        ((MainPageActivity) Objects.requireNonNull(getActivity())).setNewToken(token);
        presenter.getUserProfile(token.getAccessToken());
    }

    @Override
    public void retryGetImage(Token token) {
        ((MainPageActivity) Objects.requireNonNull(getActivity())).setNewToken(token);
        presenter.getImage(
                ((MainPageActivity) Objects.requireNonNull(getActivity())).getToken(), profileImageName);
    }

    @Override
    public void gotoLogin() {
        ((MainPageActivity) Objects.requireNonNull(getActivity())).gotoLogin();
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

    private void setGreetMessage(String userName) {
        Random random = new Random();
        switch (random.nextInt(2)) {
            case 0:
                binding.mainPageTvGreet.setText(userName + "님 오늘도 화이팅 하세요!");
                break;

            case 1:
                binding.mainPageTvGreet.setText(userName + "님 오늘도 힘내봅시다!");
                break;

            case 2:
                binding.mainPageTvGreet.setText(userName + "님 운동 힘내세요!");
                break;
        }
    }
}