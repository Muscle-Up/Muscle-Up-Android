package com.example.muscleup.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.muscleup.R;
import com.example.muscleup.databinding.ActivityLoginBinding;
import com.example.muscleup.model.data.Login;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.ui.mainPage.MainPageActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private ActivityLoginBinding binding;
    private LoginContract.Presenter presenter;

    private SharedPreferences sharedAutoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        presenter = new LoginPresenter(this);
        sharedAutoLogin = getSharedPreferences("AutoLogin", MODE_PRIVATE);

        binding.loginBtn.setOnClickListener(view -> {
            String email = binding.loginEtEmail.toString();
            String password = binding.loginEtPassword.toString();

            if (email.length() < 1 || password.length() < 1) {
                makeToast("이메일과 패스워드를 입력해주세요");
                return;
            }

            presenter.login(new Login(email, password));
        });

        binding.loginBtnBack.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public void loginFail() {
        makeToast("아이디 또는 비밀번호를 확인해주세요");
    }

    @Override
    public void setToken(Token token) {
        SharedPreferences sharedToken = getSharedPreferences("Token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedToken.edit();
        editor.putString("AccessToken", token.getAccessToken());
        editor.putString("RefreshToken", token.getRefreshToken());
        editor.apply();

        editor = sharedAutoLogin.edit();
        if (binding.loginRbAuto.isChecked()) editor.putBoolean("isAuto", true);
        else editor.putBoolean("isAuto", false);
        editor.apply();

        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }

    public void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}