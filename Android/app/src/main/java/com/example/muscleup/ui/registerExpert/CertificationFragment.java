package com.example.muscleup.ui.registerExpert;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.muscleup.R;
import com.example.muscleup.databinding.FragmentCertificationBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CertificationFragment extends Fragment {

    private FragmentCertificationBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_certification, container, false);
        binding.certificationBtnCamera.setOnClickListener(
                view -> ((RegisterExpertActivity) Objects.requireNonNull(getActivity())).takePicture());
        return binding.getRoot();
    }

    public void setImage(Uri uri) {
        binding.certificationIv.setImageURI(uri);
    }
}