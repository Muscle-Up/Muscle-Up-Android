package com.example.muscleup.signup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.example.muscleup.LoginActivity;
import com.example.muscleup.R;

public class SignUpActivity4 extends AppCompatActivity {
        Retrofit client = new RetrofitClient().getRetrofit();
        JsonPlaceHolderApi jsonPlaceHolderApi = client.create(JsonPlaceHolderApi.class);
        private TextView textViewResult;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up4);

        }

    public void backButton_Click(View view) {
        this.finish();
    }

    public void finishButton_Click(View view) {
            createPost();
        Intent intent5 = new Intent(this, LoginActivity.class);
        startActivity(intent5);
    }

        private void createPost() {
            EditText email_editText = findViewById(R.id.email_editText);
            EditText password_editText = findViewById(R.id.password_editText);

            String email = email_editText.getText().toString();
            String password = password_editText.getText().toString();
            Sex sex;

            Intent intent = getIntent();
            String name = intent.getStringExtra("name");
            int age = intent.getIntExtra("age",0);
            String str_sex = intent.getStringExtra("sex");
            if(str_sex.equals("남성")) {
                sex = Sex.MALE;
            }
            else
                sex = Sex.FEMALE;

            float height = intent.getIntExtra("height", 0);
            float weight = intent.getIntExtra("weight", 0);

            textViewResult = findViewById(R.id.result_textView);
            textViewResult.setText(name);

            Call<Void> call = jsonPlaceHolderApi.register(name, age, sex, height, weight, email, password);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@Nullable Call<Void> call, @Nullable Response<Void> response) {

                }

                @Override
                public void onFailure(@Nullable Call<Void> call, @Nullable Throwable t) {

                }
            });

//        Post post = new Post(name, age, sex, height, weight, email, password);
//
//        Call<Post> call = jsonPlaceHolderApi.createUser(post);
//
//        call.enqueue(new Callback<Post>() {
//            @Override
//            public void onResponse(Call<Post> call, Response<Post> response) {
//                if(!response.isSuccessful()) {
//                    textViewResult.setText("Code: " + response.code());
//                    return;
//                }
//
//                Post postResponse = response.body();
//
//                String content = "";
//                content += "Code: " + response.code() + "\n";
//                content += "Name: " + postResponse.getName() + "\n";
//                content += "Age: " + postResponse.getAge() + "\n";
//                content += "Sex: " + postResponse.getSex() + "\n";
//                content += "Height: " + postResponse.getHeight() + "\n";
//                content += "Weight: " + postResponse.getWeight() + "\n";
//                content += "Email: " + postResponse.getEmail() + "\n";
//                content += "Password: " + postResponse.getPassword() + "\n\n";
//
//                textViewResult.setText(content);
//            }
//
    }
}