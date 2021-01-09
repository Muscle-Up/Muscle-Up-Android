package com.example.muscleup.ui.registerExpert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.example.muscleup.R;
import com.example.muscleup.adapter.RegisterExpertPagerAdapter;
import com.example.muscleup.databinding.ActivityRegisterExpertBinding;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;
import com.example.muscleup.ui.main.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Objects;

public class RegisterExpertActivity extends AppCompatActivity implements RegisterExpertContract.View {

    private static final int REQUEST_CAMERA = 1;
    private ActivityRegisterExpertBinding binding;
    private RegisterExpertContract.Presenter presenter;
    private RegisterExpertPagerAdapter pagerAdapter;
    private byte[] certificateImg = null;
    private byte[] profileImg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_expert);
        pagerAdapter = new RegisterExpertPagerAdapter(getSupportFragmentManager(), 2);

        binding.registerExpertBtnBack.setOnClickListener(view -> finish());
        binding.registerExpertBtnRegister.setOnClickListener(view -> registerExpert());

        binding.registerExpertIndicator.createIndicator(
                2, R.drawable.indicator_default, R.drawable.indicator_selected, 0);
        binding.registerExpertVp.setAdapter(pagerAdapter);
        binding.registerExpertVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                binding.registerExpertIndicator.select(state);
            }
        });

        presenter = new RegisterExpertPresenter(this);
        presenter.getUserProfile(getToken());
    }

    @Override
    public void registerSuccess() {
        finish();
    }

    @Override
    public void setUserProfile(UserProfile userProfile) {
        profileImg = userProfile.getImage();
    }

    @Override
    public void tokenError(int errorType) {
        presenter.tokenRefresh(getRefreshToken(), errorType);
    }

    @Override
    public void retryRegisterExpert(Token token) {
        setNewToken(token);
        registerExpert();
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

    private void registerExpert() {
        String intro = pagerAdapter.getDetailFragment().getIntro();
        String name = pagerAdapter.getDetailFragment().getName();
        String date = pagerAdapter.getDetailFragment().getDate();

        if ((((intro == null) || (name == null)) || ((date == null) || (certificateImg == null))) || (profileImg == null))
            return;
        presenter.registerExpert(getToken(), intro, name, date, certificateImg, profileImg);
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

    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CAMERA) return;
        if (resultCode != RESULT_OK) return;

        try {
            assert data != null;
            InputStream in = getContentResolver().openInputStream(Objects.requireNonNull(data.getData()));

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            pagerAdapter.getCertificationFragment().setImage(bitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            certificateImg = stream.toByteArray();
        } catch (Exception ignored) {

        }
    }
}