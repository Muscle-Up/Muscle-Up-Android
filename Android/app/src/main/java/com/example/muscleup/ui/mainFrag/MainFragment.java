package com.example.muscleup.ui.mainFrag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.muscleup.ui.main.MainActivity;
import com.example.muscleup.ui.mainPage.MainPageActivity;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment implements MainFragContract.View {

    private List<Entry> weightGraphList = new ArrayList<>();
    private List<Entry> bodyFatMassGraphList = new ArrayList<>();
    private List<Entry> muscleMassGraphList = new ArrayList<>();

    private LineData weightData = new LineData();
    private LineData bodyFatMassData = new LineData();
    private LineData muscleMassData = new LineData();

    private FragmentMainBinding binding;
    private MainFragContract.Presenter presenter;

    private String bodyPostImageName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

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
        binding.mainFragSpinner.setAdapter(new SpinnerAdapter(
                Objects.requireNonNull(getActivity()).getApplicationContext(), graphList));

        binding.mainFragSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        binding.mainFragGraph.setData(muscleMassData);
                        binding.mainFragGraph.invalidate();
                        break;

                    case 1:
                        binding.mainFragGraph.setData(bodyFatMassData);
                        binding.mainFragGraph.invalidate();
                        break;

                    case 2:
                        binding.mainFragGraph.setData(weightData);
                        binding.mainFragGraph.invalidate();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        binding.mainFragSpinner.setSelection(0);

        binding.mainFragBtnDrawer.setOnClickListener(
                view -> ((MainPageActivity) getActivity()).openDrawer());

        binding.mainFragGraph.setOnClickListener(
                view -> ((MainPageActivity) getActivity()).startGraphActivity());

        binding.mainFragClPost.setOnClickListener(
                view -> ((MainPageActivity) getActivity()).startBodyPostActivity());

        presenter = new MainFragPresenter(this);
        presenter.getGraph(((MainPageActivity) getActivity()).getToken());
        presenter.getPost(((MainPageActivity) getActivity()).getToken());
        presenter.getUserProfile(((MainPageActivity) getActivity()).getToken());

        return binding.getRoot();
    }

    @Override
    public void setWeightGraph(List<Graph> weightGraphList) {
        for (Graph graph : weightGraphList) {
            this.weightGraphList.add(new Entry(graph.getValue(), graph.getId()));
        }

        binding.mainFragGraph.notifyDataSetChanged();
        binding.mainFragGraph.invalidate();
    }

    @Override
    public void setBodyFatMassGraph(List<Graph> bodyFatMassGraphList) {
        for (Graph graph : bodyFatMassGraphList) {
            this.bodyFatMassGraphList.add(new Entry(graph.getValue(), graph.getId()));
        }

        binding.mainFragGraph.notifyDataSetChanged();
        binding.mainFragGraph.invalidate();
    }

    @Override
    public void setMuscleMassGraph(List<Graph> muscleMassGraphList) {
        for (Graph graph : muscleMassGraphList) {
            this.muscleMassGraphList.add(new Entry(graph.getValue(), graph.getId()));
        }

        binding.mainFragGraph.notifyDataSetChanged();
        binding.mainFragGraph.invalidate();
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
        Bitmap profileImage = BitmapFactory.decodeByteArray(userProfile.getImage(), 0, userProfile.getImage().length);
        binding.mainPageIvProfile.setImageBitmap(profileImage);
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
    public void gotoLogin() {
        ((MainPageActivity) Objects.requireNonNull(getActivity())).gotoLogin();
    }
}