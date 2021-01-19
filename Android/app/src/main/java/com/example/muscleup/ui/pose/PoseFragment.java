package com.example.muscleup.ui.pose;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
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

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PoseFragment extends Fragment implements PoseContract.View {

    private FragmentPoseBinding binding;
    private PoseContract.Presenter presenter;
    private PoseListAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pose, container, false);
        View view = binding.getRoot();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED ||
                    getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]
                        {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        adapter = new PoseListAdapter(view12 -> ((MainPageActivity) Objects.requireNonNull(getActivity())).getImage((image, bitmap) -> adapter.addImage(image, bitmap)), () -> {
            binding.poseTvLoading.setVisibility(View.INVISIBLE);
            binding.poseBtnStart.setVisibility(View.INVISIBLE);
            binding.poseBtnRestart.setVisibility(View.VISIBLE);
            binding.poseTvInfo.setVisibility(View.INVISIBLE);
            binding.poseClStatement.setVisibility(View.VISIBLE);
        });

        binding.poseBtnStart.setOnClickListener(view1 -> {
            if (adapter.getItemCount() != 3) return;
            binding.poseBtnStart.setVisibility(View.INVISIBLE);
            binding.poseTvLoading.setVisibility(View.VISIBLE);
            for (int i = 0; i < 2; i++) {
                RequestBody inputImage = adapter.getPose(i);
                presenter.analyzePose(
                        ((MainPageActivity) Objects.requireNonNull(getActivity())).getToken(), inputImage, i);
            }
        });
        binding.poseRv.setAdapter(adapter);
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
            RequestBody inputImage = adapter.getPose(i);
            presenter.analyzePose(
                    token.getAccessToken(), inputImage, i);
        }
    }

    @Override
    public void gotoLogin() {
        ((MainPageActivity) Objects.requireNonNull(getActivity())).gotoLogin();
    }
}