package com.example.muscleup.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.muscleup.R;

public class SignUpActivity2 extends AppCompatActivity {

    private EditText heightEditText;
    private EditText weightEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        heightEditText = findViewById(R.id.height_editText);
        weightEditText = findViewById(R.id.weight_editText);
    }

    public void onBackButtonClick(View view) {
        this.finish();
    }

    public void onNextButtonInSignUp2Click(View view) {
        if ( checkValidInput() == false ) return;

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int age = intent.getIntExtra("age", 0);
        String sex = intent.getStringExtra("sex");

        String height = heightEditText.getText().toString();
        String weight = weightEditText.getText().toString();

        Intent intent3 = new Intent(this, SignUpActivity3.class);
        intent3.putExtra("name", name);
        intent3.putExtra("age", age);
        intent3.putExtra("sex", sex);

        if( height.equals("") )
            intent3.putExtra("height",  0);
        else
            intent3.putExtra("height", Integer.parseInt(height));

        if( weight.equals("") )
            intent3.putExtra("weight", 0);
        else
            intent3.putExtra("weight", Integer.parseInt(weight));

        startActivity(intent3);
    }

    private boolean checkValidInput()
    {
        if  ( heightEditText.getText().toString().trim().equals("") )
            heightEditText.setText( "" );

        if  ( weightEditText.getText().toString().trim().equals("") )
            weightEditText.setText( "" );

        return true;
    }
}