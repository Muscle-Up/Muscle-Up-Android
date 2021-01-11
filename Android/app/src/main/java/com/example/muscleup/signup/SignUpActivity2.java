package com.example.muscleup.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.muscleup.R;

public class SignUpActivity2 extends AppCompatActivity {

    EditText height_editText;
    EditText weight_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        height_editText = findViewById(R.id.height_editText);
        weight_editText = findViewById(R.id.weight_editText);
    }


    public void backButton_Click(View view) {
        this.finish();
    }
    public void nextButton_Click(View view) {
        if ( checkValidInput() == false ) return;

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int age = intent.getIntExtra("age", 0);
        String sex = intent.getStringExtra("sex");

        String height = height_editText.getText().toString();
        String weight = weight_editText.getText().toString();

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
        if  ( height_editText.getText().toString().trim().equals("") )
            height_editText.setText( "" );

        if  ( weight_editText.getText().toString().trim().equals("") )
            weight_editText.setText( "" );

        return true;
    }
}