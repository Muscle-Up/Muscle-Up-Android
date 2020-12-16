package com.example.muscleup.ui.changeProfile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.muscleup.R;
import com.example.muscleup.databinding.ActivityEditProfileBinding;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;
import com.example.muscleup.ui.main.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity implements EditProfileContract.View {

    private ActivityEditProfileBinding binding;
    private EditProfileContract.Presenter presenter;

    private String inputName;
    private int inputAge;
    private float inputHeight;
    private float inputWeight;
    private byte[] inputImage;

    private UserProfile curUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);

        binding.editProfileBtnBack.setOnClickListener(view -> {
            finish();
        });

        binding.editProfileBtnGallery.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 0);
        });

        binding.editProfileBtnEdit.setOnClickListener(view -> {
            inputName = binding.editProfileEtName.getText().toString();
            inputAge = Integer.parseInt(binding.editProfileEtAge.getText().toString());
            inputHeight = Float.parseFloat(binding.editProfileEtHeight.getText().toString());
            inputWeight = Float.parseFloat(binding.editProfileEtWeight.getText().toString());

            if (binding.editProfileEtName.getText().length() < 1) return;
            if (binding.editProfileEtAge.getText().length() < 1) return;
            if (binding.editProfileEtHeight.getText().length() < 1) return;
            if (binding.editProfileEtWeight.getText().length() < 1) return;

            presenter.editProfile(
                    getAccessToken(), inputName, inputAge, curUserProfile.getSex(), inputHeight,inputWeight,inputImage);
        });

        presenter = new EditProfilePresenter(this);
        presenter.getProfile(getAccessToken());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                try {
                    assert data != null;
                    InputStream in = getContentResolver().openInputStream(Objects.requireNonNull(data.getData()));

                    Bitmap image = BitmapFactory.decodeStream(in);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    inputImage = stream.toByteArray();

                    assert in != null;
                    in.close();

                    binding.editProfileIv.setImageBitmap(image);
                } catch (Exception ignored) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void setCurProfile(UserProfile userProfile) {
        Bitmap image = BitmapFactory.decodeByteArray(
                userProfile.getImage(), 0, userProfile.getImage().length);
        String name = userProfile.getName();
        int age = userProfile.getAge();
        float height = userProfile.getHeight();
        float weight = userProfile.getWeight();
        curUserProfile = userProfile;
        inputImage = userProfile.getImage();

        binding.editProfileIv.setImageBitmap(image);
        binding.editProfileEtName.setText(name);
        binding.editProfileEtAge.setText(age);
        binding.editProfileEtHeight.setText(String.valueOf(height));
        binding.editProfileEtWeight.setText(String.valueOf(weight));
    }

    @Override
    public void editSuccess() {
        Toast.makeText(this, "성공적으로 변경되었습니다", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tokenError(int errorType) {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        presenter.tokenRefresh(sharedPreferences.getString("RefreshToken", "null"), errorType);
    }

    @Override
    public void retryLoadUserProfile(Token token) {
        setNewToken(token);
        presenter.getProfile(token.getAccessToken());
    }

    @Override
    public void retryEditUserProfile(Token token) {
        setNewToken(token);
        presenter.editProfile(token.getAccessToken(),
                inputName, inputAge, curUserProfile.getSex(), inputHeight, inputWeight, inputImage);
    }

    @Override
    public void gotoLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setNewToken(Token token) {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("AccessToken", token.getAccessToken());
        editor.putString("RefreshToken", token.getRefreshToken());
        editor.apply();
    }

    private String getAccessToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        return sharedPreferences.getString("AccessToken", "null");
    }
}