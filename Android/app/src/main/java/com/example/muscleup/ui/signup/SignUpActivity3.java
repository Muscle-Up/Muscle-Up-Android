package com.example.muscleup.ui.signup;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import com.example.muscleup.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUpActivity3 extends AppCompatActivity {
    final private String TAG = "MuscleUp";
    ImageButton cameraButton;
    ImageView profileImage;

    private String imageFilePath;
    private Uri photoUri, imageUri = null;

    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private static final int REQUEST_ALBUM = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);

        profileImage = findViewById(R.id.set_profile_picture);
        cameraButton = findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_GRANTED &&
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "권한 설정 완료");
                    }
                    else {
                        Log.d(TAG, "권한 설정 요청");
                        ActivityCompat.requestPermissions(SignUpActivity3.this, new String[]
                                {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }

                PopupMenu pop = new PopupMenu(getApplicationContext(), view);
                getMenuInflater().inflate(R.menu.profile_menu, pop.getMenu());

                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.camera:
                                sendTakePhotoIntent();
                                break;
                            case R.id.gallery:
                                getAlbum();
                                break;
                        }
                        return true;
                    }
                });
                pop.show();
            }
        });

    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      // prefix
                ".jpg",        // suffix
                storageDir          // directory
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private void sendTakePhotoIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();  // 사진찍은 후 저장할 임시 파일
            } catch (IOException ex) {
                Toast.makeText(getApplicationContext(), "이미지 생성 실패", Toast.LENGTH_LONG).show();
            }

            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    //앨범 호출
    public void getAlbum() {
        Intent intent = new Intent( Intent.ACTION_PICK );
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult( intent, REQUEST_ALBUM);
    }

    // Permission에 대한 승인 완료확인 코드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == 0) {
            Toast.makeText(this, "카메라 권한 승인완료", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "카메라 권한 승인 거절", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK) {
            Toast.makeText(getApplicationContext(), "onActivityResult : RESULT_NOT_OK", Toast.LENGTH_SHORT).show();
        }

        else {
            switch (requestCode) {
                case REQUEST_ALBUM: // 앨범
                    imageUri = data.getData();
                    profileImage.setImageURI(imageUri);
                    break;
                case REQUEST_IMAGE_CAPTURE:     // 카메라
                    profileImage.setImageURI(photoUri);
                    imageUri = photoUri;
                    break;
            }
        }
    }

    public void onBackButtonClick(View view) {
        this.finish();
    }

    public void onNextButtonInSignUp3Click(View view) {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int age = intent.getIntExtra("age", 0);
        String sex = intent.getStringExtra("sex");
        int height = intent.getIntExtra("height", 0);
        int weight = intent.getIntExtra("weight", 0);

        Intent intent4 = new Intent(this, SignUpActivity4.class);
        intent4.putExtra("name", name);
        intent4.putExtra("age", age);
        intent4.putExtra("sex", sex);
        intent4.putExtra("height", height);
        intent4.putExtra("weight", weight);

        if(imageUri == null) {
            intent4.putExtra("image", "");
        }
        else
            intent4.putExtra("image", imageUri.toString());

        startActivity(intent4);
    }

}
