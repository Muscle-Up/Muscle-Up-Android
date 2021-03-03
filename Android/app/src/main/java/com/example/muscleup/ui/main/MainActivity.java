package com.example.muscleup.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.muscleup.R;
import com.example.muscleup.databinding.ActivityMainBinding;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.ui.login.LoginActivity;
import com.example.muscleup.ui.mainPage.MainPageActivity;
import com.example.muscleup.ui.signup.SignUpActivity;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private ActivityMainBinding binding;
    private MainContract.Presenter presenter;

    private SharedPreferences sharedAutoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        presenter = new MainPresenter(this);
        sharedAutoLogin = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        if(sharedAutoLogin.getBoolean("AutoLogin", false)){
            SharedPreferences sharedToken = getSharedPreferences("Token", MODE_PRIVATE);
            presenter.getNewToken(sharedToken.getString("refreshToken", "null"));
        }

        binding.mainBtnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.mainBtnSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void setToken(Token token) {
        SharedPreferences sharedToken = getSharedPreferences("Token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedToken.edit();
        editor.putString("accessToken", token.getAccessToken());
        editor.putString("refreshToken", token.getRefreshToken());
        editor.apply();

        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }
}