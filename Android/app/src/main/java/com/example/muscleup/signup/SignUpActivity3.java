package com.example.muscleup.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.muscleup.R;

public class SignUpActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);
    }
    public void backButton_Click(View view) {
        this.finish();
    }
    public void nextButton_Click(View view) {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int age = intent.getIntExtra("age",0);
        String sex = intent.getStringExtra("sex");
        int height = intent.getIntExtra("height", 0);
        int weight = intent.getIntExtra("weight", 0);

        Intent intent4 = new Intent(this, SignUpActivity4.class);
        intent4.putExtra("name", name);
        intent4.putExtra("age", age);
        intent4.putExtra("sex", sex);
        intent4.putExtra("height", height);
        intent4.putExtra("weight", weight);

        startActivity(intent4);
    }
}
