package com.example.muscleup.ui.pose;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.muscleup.R;
import com.example.muscleup.databinding.FragmentPoseBinding;
import com.example.muscleup.model.data.Pose;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.ui.mainPage.MainPageActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class PoseFragment extends Fragment implements PoseContract.View {

    private FragmentPoseBinding binding;
    private PoseContract.Presenter presenter;
    private PoseListAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pose, container, false);
        View view = binding.getRoot();

        adapter = new PoseListAdapter(view1 -> {
            if (adapter.getItemCount() != 3) return;
            ((MainPageActivity) Objects.requireNonNull(getActivity())).getImage(image -> adapter.addImage(image));
        }, () -> {
            binding.poseTvLoading.setVisibility(View.INVISIBLE);
            binding.poseBtnRestart.setVisibility(View.VISIBLE);
            binding.poseTvInfo.setVisibility(View.INVISIBLE);
            binding.poseClStatement.setVisibility(View.VISIBLE);
        });

        binding.poseBtnStart.setOnClickListener(view1 -> {
            if (adapter.getItemCount() != 3) return;
            binding.poseBtnStart.setVisibility(View.INVISIBLE);
            binding.poseTvLoading.setVisibility(View.VISIBLE);
            for (int i = 0; i < 2; i++) {
                presenter.analyzePose(
                        ((MainPageActivity) Objects.requireNonNull(getActivity())).getToken(), adapter.getPose(i), i);
            }
        });
        adapter.addUploadView();

        presenter = new PosePresenter(this);
        return view;
    }

    @Override
    public void setPose(List<Pose> poseList, int position) {
        adapter.setPose(poseList, position);
    }

    @Override
    public void tokenError() {
        presenter.tokenRefresh(
                ((MainPageActivity) Objects.requireNonNull(getActivity())).getRefreshToken());
    }

    @Override
    public void retryAnalyzePose(Token token) {
        ((MainPageActivity) Objects.requireNonNull(getActivity())).setNewToken(token);
        for (int i = 0; i < 2; i++) {
            presenter.analyzePose(
                    token.getAccessToken(), adapter.getPose(i), i);
        }
    }

    @Override
    public void gotoLogin() {
        ((MainPageActivity) Objects.requireNonNull(getActivity())).gotoLogin();
    }
}