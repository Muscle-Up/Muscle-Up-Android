package com.example.muscleup.signup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.muscleup.LoginActivity;
import com.example.muscleup.R;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity4 extends AppCompatActivity {

    private  ApiService apiService;

    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4);

        emailEditText = findViewById(R.id.email_editText);
        passwordEditText = findViewById(R.id.password_editText);
        passwordCheck = findViewById(R.id.check_password_editText);

        apiService = ApiUtils.getAPIService();

    }

   private void createPost() {

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        Sex sex;

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int age = intent.getIntExtra("age", 0);
        String str_sex = intent.getStringExtra("sex");
        if (str_sex.equals("남성")) {
            sex = Sex.MALE;
        } else
            sex = Sex.FEMALE;

        int height = intent.getIntExtra("height", 0);
        int weight = intent.getIntExtra("weight", 0);

        String path = intent.getStringExtra("image");

        Bitmap bitImage = null;
        byte[] image = null;

        if(!path.equals("")) {
            bitImage = uriToBitmap(path);
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitImage.compress(Bitmap.CompressFormat.JPEG, 70, byteArray);
            image = byteArray.toByteArray();
        }

       Call<Void> call = apiService.register(name, age, sex, height, weight, email, password, image);

       call.enqueue(new Callback<Void>() {
           @Override
           public void onResponse(@Nullable Call<Void> call, @Nullable Response<Void> response) {

               if (!response.isSuccessful()) {
                   Log.e("연결이 비정상적 : ", "error code : " + response.code());
                   return;
               }
               Log.d("연결이 성공적 : ", response.body().toString());
           }

           @Override
           public void onFailure(@Nullable Call<Void> call, @Nullable Throwable t) {
               Log.e("연결 실패", t.getMessage());
           }


       });
   }

   protected Bitmap uriToBitmap(String path) {
       Bitmap bitmap = null;
       if(!path.equals("")) {
           Uri uri = Uri.parse(path);
           try {
               bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
       return bitmap;
   }

    public void onBackButtonClick(View view) {
        this.finish();
    }

    public void onFinishButtonClick(View view)  {
        if ( checkValidInput() == false ) return;

        createPost();
        Toast.makeText(this, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
        Intent intent5 = new Intent(this, LoginActivity.class);
        startActivity(intent5);
    }

    private boolean checkValidInput()
    {
        if ( emailEditText.length() == 0 )
        {
            emailEditText.setError("이메일을 입력하십시오.");
            return false;
        }

        Pattern p = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
        );
        Matcher m = p.matcher(emailEditText.getText().toString());

        if(!m.matches()) {
            Toast.makeText(this, "Email 형식으로 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }


        //  암호 입력 검사
        if ( passwordEditText.length() == 0 )
        {
            passwordEditText.setError("비밀번호를 입력하십시오.");
            return false;
        }

        // 암호 일치 검사

        if(!passwordEditText.getText().toString().equals(passwordCheck.getText().toString())) {
            passwordCheck.setError("비밀번호가 일치하지 않습니다.");
            return false;
        }

        return true;
    }

}