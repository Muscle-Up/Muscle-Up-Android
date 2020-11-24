package com.example.muscleup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void backButton_Click(View view) {
        this.finish();
    }
    public void nextButton_Click(View view) {
        Intent intent = new Intent(this, SignUpActivity2.class);
        startActivity(intent);
    }
}