package com.example.muscleup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUpActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
    }

    public void backButton_Click(View view) {
        this.finish();
    }
    public void nextButton_Click(View view) {
        Intent intent = new Intent(this, SignUpActivity3.class);
        startActivity(intent);
    }
}