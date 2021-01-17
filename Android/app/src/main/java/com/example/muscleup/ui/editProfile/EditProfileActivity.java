package com.example.muscleup.ui.editProfile;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.loader.content.CursorLoader;

import com.example.muscleup.R;
import com.example.muscleup.databinding.ActivityEditProfileBinding;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;
import com.example.muscleup.ui.main.MainActivity;

import java.io.File;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileActivity extends AppCompatActivity implements EditProfileContract.View {

    private ActivityEditProfileBinding binding;
    private EditProfileContract.Presenter presenter;

    private RequestBody inputName;
    private RequestBody inputAge;
    private RequestBody inputHeight;
    private RequestBody inputWeight;
    private MultipartBody.Part inputImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        binding.editProfileBtnBack.setOnClickListener(view -> finish());

        binding.editProfileBtnGallery.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, 0);
        });

        binding.editProfileBtnEdit.setOnClickListener(view -> {
            inputName = createPartFormString(binding.editProfileEtName.getText().toString());
            inputAge = createPartFormString(binding.editProfileEtAge.getText().toString());
            inputHeight = createPartFormString(binding.editProfileEtHeight.getText().toString());
            inputWeight = createPartFormString(binding.editProfileEtWeight.getText().toString());

            /*inputName = binding.editProfileEtName.getText().toString();
            inputAge = Integer.parseInt(binding.editProfileEtAge.getText().toString());
            inputHeight = Integer.parseInt(binding.editProfileEtHeight.getText().toString());
            inputWeight = Integer.parseInt(binding.editProfileEtWeight.getText().toString());*/

            if (binding.editProfileEtName.getText().toString().length() < 1) return;
            if (binding.editProfileEtAge.getText().toString().length() < 1) return;
            if (binding.editProfileEtHeight.getText().toString().length() < 1) return;
            if (binding.editProfileEtWeight.getText().toString().length() < 1) return;

            presenter.editProfile(
                    getAccessToken(), inputName, inputAge, inputHeight, inputWeight, inputImage);
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

                    Uri uri = data.getData();
                    File file = new File(getRealPathFromURI(uri));
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    inputImage = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

                    Log.d("EditProfileActivity", "onActivityResult: " + getRealPathFromURI(uri));

                    binding.editProfileIv.setImageURI(uri);
                } catch (Exception ignored) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void setCurProfile(UserProfile userProfile) {
        String name = userProfile.getName();
        int age = userProfile.getAge();
        int height = userProfile.getHeight();
        int weight = userProfile.getWeight();

        binding.editProfileEtName.setText(name);
        binding.editProfileEtAge.setText(String.valueOf(age));
        binding.editProfileEtHeight.setText(String.valueOf(height));
        binding.editProfileEtWeight.setText(String.valueOf(weight));

        if (userProfile.getImage() != null) {
            Uri uri = Uri.parse(new String(userProfile.getImage(), StandardCharsets.UTF_8));
            binding.editProfileIv.setImageURI(uri);
            inputImage = prepareFilePart(uri);
        }
    }

    private RequestBody createPartFormString(String text) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), text);
    }

    private String getRealPathFromURI(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();

        assert cursor != null;
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        String result = cursor.getString(columnIndex);
        cursor.close();
        return result;
    }

    private MultipartBody.Part prepareFilePart(Uri uri) {
        File file = new File(getRealPathFromURI(uri));
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData("image", file.getName(), requestBody);
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
                inputName, inputAge, inputHeight, inputWeight, inputImage);
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