package com.example.muscleup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.muscleup.ui.login.LoginActivity;

public class FindPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
    }

    public void OnClick1(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}