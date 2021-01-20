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
    EditText nameEditText;
    EditText ageEditText;
    RadioGroup sexRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameEditText = findViewById(R.id.name_editText);
        ageEditText = findViewById(R.id.age_editText);
        sexRadioGroup = findViewById(R.id.sex_radioGroup);
    }

    public void onBackButtonClick(View view) {
        this.finish();
    }

    public void onNextButtonInSignUp1Click(View view) {
        Intent intent = new Intent(this, SignUpActivity2.class);

        int radioId = sexRadioGroup.getCheckedRadioButtonId();
        RadioButton sexRadioButton = findViewById(radioId);

        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String sex = sexRadioButton.getText().toString();

        if (nameEditText.length() != 0 && ageEditText.length() != 0 && radioId != 0)
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