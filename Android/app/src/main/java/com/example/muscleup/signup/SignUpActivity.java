package com.example.muscleup.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.muscleup.R;

public class SignUpActivity extends AppCompatActivity {
    EditText name_editText;
    EditText age_editText;
    RadioGroup sex_rg;
    RadioButton rb1;
    RadioButton rb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name_editText = findViewById(R.id.name_editText);
        age_editText = findViewById(R.id.age_editText);
        sex_rg = findViewById(R.id.sex_radioGroup);
        rb1 = findViewById(R.id.man_radioButton);
        rb2 = findViewById(R.id.woman_radioButton);
    }

    public void backButton_Click(View view) {
        this.finish();
    }

    public void nextButton_Click(View view) {
        Intent intent = new Intent(this, SignUpActivity2.class);

        int radio_id = sex_rg.getCheckedRadioButtonId();
        RadioButton sex_rb = findViewById(radio_id);

        String name = name_editText.getText().toString();
        String age = age_editText.getText().toString();
        String sex = sex_rb.getText().toString();

        if (name_editText.length() != 0 && age_editText.length() != 0 && radio_id != 0)
        {
            intent.putExtra("name", name);
            intent.putExtra("age", Integer.parseInt(age));
            intent.putExtra("sex", sex);
            startActivity(intent);
        }
        else
            Toast.makeText(this, "모두 입력해 주세요", Toast.LENGTH_SHORT).show();
    }
}