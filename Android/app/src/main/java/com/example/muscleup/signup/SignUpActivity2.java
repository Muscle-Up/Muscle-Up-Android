package com.example.muscleup.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.muscleup.R;

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
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int age = intent.getIntExtra("age", 0);
        String sex = intent.getStringExtra("sex");

        EditText height_editText = findViewById(R.id.height_editText);
        EditText weight_editText = findViewById(R.id.weight_editText);

        String height = height_editText.getText().toString();
        String weight = weight_editText.getText().toString();

        Intent intent3 = new Intent(this, SignUpActivity4.class);
        intent3.putExtra("name", name);
        intent3.putExtra("age", age);
        intent3.putExtra("sex", sex);
        intent3.putExtra("height", Integer.parseInt(height));
        intent3.putExtra("weight", Integer.parseInt(weight));

        startActivity(intent3);
    }
}