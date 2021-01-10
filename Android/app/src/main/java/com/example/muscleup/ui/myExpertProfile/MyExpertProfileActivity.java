package com.example.muscleup.ui.myExpertProfile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.muscleup.R;
import com.example.muscleup.databinding.ActivityMyExpertProfileBinding;
import com.example.muscleup.model.data.ExpertProfile;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;
import com.example.muscleup.ui.main.MainActivity;

public class MyExpertProfileActivity extends AppCompatActivity implements MyExpertProfileContract.View {

    private ActivityMyExpertProfileBinding binding;
    private MyExpertProfileContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_expert_profile);
        setContentView(R.layout.activity_my_expert_profile);

        binding.myExpertBtnBack.setOnClickListener(view -> finish());

        presenter = new MyExpertProfilePresenter(this);
        presenter.getMyExpertProfile(getToken());
        presenter.getUserProfile(getToken());
    }

    @Override
    public void setMyExpertProfile(ExpertProfile expertProfile) {
        Bitmap certificateImage = BitmapFactory.decodeByteArray(
                expertProfile.getCertificateImage(), 0, expertProfile.getCertificateImage().length);
        Bitmap image = BitmapFactory.decodeByteArray
                (expertProfile.getUserImage(), 0, expertProfile.getUserImage().length);

        binding.myExpertIvCertificate.setImageBitmap(certificateImage);
        binding.myExpertIvProfile.setImageBitmap(image);
        binding.myExpertTvIntro.setText(expertProfile.getIntroduction());
        binding.myExpertTvCertificateName.setText(expertProfile.getCertificateName());
        binding.myExpertTvCertificateDate.setText(expertProfile.getAcquisitionDate());
    }

    @Override
    public void setUserProfile(UserProfile userProfile) {
        binding.myExpertTvName.setText(userProfile.getName() + " 트레이너");
        binding.myExpertTvAge.setText(userProfile.getAge());
        binding.myExpertTvSex.setText(userProfile.getSex());
    }

    @Override
    public void tokenError(int errorType) {
        presenter.tokenRefresh(getRefreshToken(), errorType);
    }

    @Override
    public void retryGetMyExpertProfile(Token token) {
        setNewToken(token);
        presenter.getMyExpertProfile(getToken());
    }

    @Override
    public void retryGetUserProfile(Token token) {
        setNewToken(token);
        presenter.getUserProfile(getToken());
    }

    @Override
    public void gotoLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        return sharedPreferences.getString("accessToken", "");
    }

    public String getRefreshToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        return sharedPreferences.getString("RefreshToken", "null");
    }

    public void setNewToken(Token token) {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("AccessToken", token.getAccessToken());
        editor.putString("RefreshToken", token.getRefreshToken());
        editor.apply();
    }
}