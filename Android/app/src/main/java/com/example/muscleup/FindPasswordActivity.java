package com.example.muscleup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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