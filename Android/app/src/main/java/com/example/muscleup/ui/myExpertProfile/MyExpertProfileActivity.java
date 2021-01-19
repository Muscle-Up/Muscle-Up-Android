package com.example.muscleup.ui.myExpertProfile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.muscleup.R;
import com.example.muscleup.databinding.ActivityMyExpertProfileBinding;
import com.example.muscleup.model.ExpertModel;
import com.example.muscleup.model.data.ExpertProfile;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;
import com.example.muscleup.ui.main.MainActivity;

public class MyExpertProfileActivity extends AppCompatActivity implements MyExpertProfileContract.View {

    private ActivityMyExpertProfileBinding binding;
    private MyExpertProfileContract.Presenter presenter;

    private String profileImageName;
    private String certificateImageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_expert_profile);

        binding.myExpertBtnBack.setOnClickListener(view -> finish());

        presenter = new MyExpertProfilePresenter(this);
        presenter.getMyExpertProfile(getToken());
        presenter.getUserProfile(getToken());
    }

    @Override
    public void setMyExpertProfile(ExpertProfile expertProfile) {
        binding.myExpertTvIntro.setText(expertProfile.getIntroduction());
        binding.myExpertTvName.setText(expertProfile.getName() + " 트레이너");
        binding.myExpertTvAge.setText(String.valueOf(expertProfile.getAge()));
        binding.myExpertTvCertificateName.setText(expertProfile.getCertificateName());
        binding.myExpertTvCertificateDate.setText(expertProfile.getAcquisitionDate());

        profileImageName = expertProfile.getUserImage();
        certificateImageName = expertProfile.getCertificateImage();
        presenter.getImage(getToken(), profileImageName, ExpertModel.IMAGE_TYPE_PROFILE);
        presenter.getImage(getToken(), certificateImageName, ExpertModel.IMAGE_TYPE_CERTIFICATE);
    }

    @Override
    public void setUserProfile(UserProfile userProfile) {
        if(userProfile.getSex().equals("MALE")) binding.myExpertTvSex.setText("남성");
        else binding.myExpertTvSex.setText("여성");
    }

    @Override
    public void setProfileImage(byte[] image) {
        if (image != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            binding.myExpertIvProfile.setImageBitmap(bitmap);
        }
    }

    @Override
    public void setCertificateImage(byte[] image) {
        if (image != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            binding.myExpertIvCertificate.setImageBitmap(bitmap);
        }
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
    public void retryGetImage(Token token) {
        setNewToken(token);
        presenter.getImage(getToken(), profileImageName, ExpertModel.IMAGE_TYPE_PROFILE);
        presenter.getImage(getToken(), certificateImageName, ExpertModel.IMAGE_TYPE_CERTIFICATE);
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