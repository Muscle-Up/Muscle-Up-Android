package com.example.muscleup.model.callback;

import com.example.muscleup.model.data.ExpertProfile;

public interface LoadMyExpertProfileListener {
    void loadMyExpertProfile(ExpertProfile expertProfile);

    void onWrongToken();
}
