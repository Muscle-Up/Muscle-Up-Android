package com.example.muscleup.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.muscleup.ui.registerExpert.CertificationFragment;
import com.example.muscleup.ui.registerExpert.DetailFragment;

public class RegisterExpertPagerAdapter extends FragmentPagerAdapter {

    private CertificationFragment certificationFragment;
    private DetailFragment detailFragment;

    private int pageCount;

    public RegisterExpertPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.pageCount = behavior;

        certificationFragment = new CertificationFragment();
        detailFragment = new DetailFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return certificationFragment;

            case 1:
                return detailFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    public CertificationFragment getCertificationFragment() {
        return certificationFragment;
    }

    public DetailFragment getDetailFragment() {
        return detailFragment;
    }
}
