package com.example.muscleup.ui.registerExpert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.muscleup.R;
import com.example.muscleup.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        return binding.getRoot();
    }

    public String getIntro() {
        String intro = binding.detailEtIntro.getText().toString();
        if (intro.length() < 1) return null;
        return intro;
    }

    public String getName() {
        String name = binding.detailEtName.getText().toString();
        if (name.length() < 1) return null;
        return name;
    }

    public String getDate() {
        String date = binding.detailEtDate.getText().toString();
        if (date.length() < 1) return null;
        return date;
    }
}