package com.example.muscleup.ui.registerExpert;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.loader.content.CursorLoader;
import androidx.viewpager.widget.ViewPager;

import com.example.muscleup.R;
import com.example.muscleup.adapter.RegisterExpertPagerAdapter;
import com.example.muscleup.databinding.ActivityRegisterExpertBinding;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.ui.main.MainActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterExpertActivity extends AppCompatActivity implements RegisterExpertContract.View {

    private static final int REQUEST_CAMERA = 1;
    private static int FRAGMENT_CERTIFICATION = 100;
    private static int FRAGMENT_DETAIL = 101;
    public int curFragment;

    private ActivityRegisterExpertBinding binding;
    private RegisterExpertContract.Presenter presenter;
    private RegisterExpertPagerAdapter pagerAdapter;
    private MultipartBody.Part certificateImg = null;

    private Uri uri = null;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_expert);
        pagerAdapter = new RegisterExpertPagerAdapter(getSupportFragmentManager(), 2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

        binding.registerExpertBtnBack.setOnClickListener(view -> finish());
        binding.registerExpertBtnRegister.setOnClickListener(view -> {
            if (curFragment == FRAGMENT_CERTIFICATION) {
                binding.registerExpertVp.setCurrentItem(1, true);
            } else if (curFragment == FRAGMENT_DETAIL) registerExpert();
        });

        binding.registerExpertIndicator.createIndicator(
                2, R.drawable.indicator_default, R.drawable.indicator_selected, 0);
        binding.registerExpertVp.setAdapter(pagerAdapter);
        binding.registerExpertVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.registerExpertIndicator.select(position);
                if (position == 0) {
                    binding.registerExpertBtnRegister.setText("다음");
                    curFragment = FRAGMENT_CERTIFICATION;
                } else if (position == 1) {
                    binding.registerExpertBtnRegister.setText("등록하기");
                    curFragment = FRAGMENT_DETAIL;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        curFragment = FRAGMENT_CERTIFICATION;

        presenter = new RegisterExpertPresenter(this);
    }

    @Override
    public void registerSuccess() {
        Toast.makeText(this, "성공적으로 등록되었습니다", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void tokenError() {
        presenter.tokenRefresh(getRefreshToken());
    }

    @Override
    public void retryRegisterExpert(Token token) {
        setNewToken(token);
        registerExpert();
    }


    @Override
    public void gotoLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void registerExpert() {
        String intro = pagerAdapter.getDetailFragment().getIntro();
        String name = pagerAdapter.getDetailFragment().getName();
        String date = pagerAdapter.getDetailFragment().getDate();

        if (pagerAdapter.getDetailFragment().getIntro() == null) return;
        if (pagerAdapter.getDetailFragment().getName() == null) return;
        if (pagerAdapter.getDetailFragment().getDate() == null) return;
        if (certificateImg == null) return;

        RequestBody introRequest = createPartFormString(intro);
        RequestBody nameRequest = createPartFormString(name);
        RequestBody dateRequest = createPartFormString(date);

        presenter.registerExpert(getToken(), introRequest, nameRequest, dateRequest, certificateImg);
    }

    public String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        return sharedPreferences.getString("AccessToken", "");
    }

    public String getRefreshToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        return sharedPreferences.getString("RefreshToken", "null");
    }

    public void setNewToken(Token token) {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("AccessToken", token.getAccessToken());
        editor.putString("RefreshToken", token.getRefreshToken());
        editor.apply();
    }

    public void takePicture() {
        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(), "muscleUpRegisterExpert.jpg");
        Uri uri = FileProvider.getUriForFile(this, getPackageName(), file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CAMERA);*/

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getApplicationContext(), "이미지 생성 실패", Toast.LENGTH_LONG).show();
            }

            if (photoFile != null) {
                uri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, ".jpg", storageDir);
        imagePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CAMERA) return;
        if (resultCode != RESULT_OK) return;

        try {
            pagerAdapter.getCertificationFragment().setImage(uri);
            File file = new File(imagePath);
            Log.d("RegisterExpertActivity", "onActivityResult: " + file.toString());
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            certificateImg = MultipartBody.Part.createFormData("certificateImage", file.getName(), requestBody);
        } catch (Exception ignored) {

        }
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

    private RequestBody createPartFormString(String text) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), text);
    }
}