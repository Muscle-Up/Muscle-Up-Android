package com.example.muscleup.ui.changePassword;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.muscleup.R;
import com.example.muscleup.databinding.ActivityChangePasswordBinding;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.ui.main.MainActivity;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordContract.View {

    private ActivityChangePasswordBinding binding;
    private ChangePasswordContract.Presenter presenter;

    private String newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);

        binding.changePassBtnEdit.setOnClickListener(view -> {
            String newPass = binding.changePassEtNew.getText().toString();
            String checkPass = binding.changePassEtCheck.getText().toString();

            if (newPass.length() < 1 || checkPass.length() < 1) return;

            SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
            newPassword = newPass;
            if (newPass == checkPass) presenter.changePassword(
                    sharedPreferences.getString("AccessToken", "null"), newPassword);
        });

        binding.changePassBtnBack.setOnClickListener(view -> {
            finish();
        });

        presenter = new ChangePasswordPresenter(this);
    }

    @Override
    public void changeSuccess() {
        binding.changePassEtNew.setText("");
        binding.changePassEtCheck.setText("");
        Toast.makeText(this, "비밀번호를 변경했습니다", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tokenError() {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        presenter.TokenRefresh(sharedPreferences.getString("RefreshToken", "null"));
    }

    @Override
    public void retryChangePassword(Token token) {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("AccessToken", token.getAccessToken());
        editor.putString("RefreshToken", token.getRefreshToken());
        editor.apply();

        presenter.changePassword(
                sharedPreferences.getString("AccessToken", "null"), newPassword);
    }

    @Override
    public void gotoLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}