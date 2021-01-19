package com.example.muscleup.ui.mainPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.example.muscleup.R;
import com.example.muscleup.databinding.ActivityMainPageBinding;
import com.example.muscleup.dialog.CustomDialog;
import com.example.muscleup.model.callback.CustomDialogListener;
import com.example.muscleup.model.callback.LoadPoseImageListener;
import com.example.muscleup.model.data.Token;
import com.example.muscleup.model.data.UserProfile;
import com.example.muscleup.ui.changePassword.ChangePasswordActivity;
import com.example.muscleup.ui.editProfile.EditProfileActivity;
import com.example.muscleup.ui.graph.GraphActivity;
import com.example.muscleup.ui.login.LoginActivity;
import com.example.muscleup.ui.main.MainActivity;
import com.example.muscleup.ui.mainFrag.MainFragment;
import com.example.muscleup.ui.myExpertProfile.MyExpertProfileActivity;
import com.example.muscleup.ui.pose.PoseFragment;
import com.example.muscleup.ui.registerExpert.RegisterExpertActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainPageActivity extends AppCompatActivity implements MainPageContract.View {

    private MainFragment mainPageFragment = new MainFragment();
    private PoseFragment poseFragment = new PoseFragment();

    private ActivityMainPageBinding binding;
    private MainPageContract.Presenter presenter;
    private LoadPoseImageListener loadPoseImageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_page);

        changeFragment(mainPageFragment);
        binding.mainBnv.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_main:
                    binding.mainDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    changeFragment(mainPageFragment);
                    break;

                case R.id.menu_pose:
                    binding.mainDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    changeFragment(poseFragment);
                    break;

                case R.id.menu_qna:
                    binding.mainDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    //Todo : QnA Fragment 로 이동하는 코드 추가
                    break;

                case R.id.menu_messenger:
                    binding.mainDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    //Todo : Messenger Fragment 로 이동하는 코드 추가
                    break;
            }
            return true;
        });

        binding.drawerBtnMyExpertProfile.setOnClickListener(view -> {
            Intent intent = new Intent(this, MyExpertProfileActivity.class);
            startActivity(intent);
        });

        binding.drawerBtnRegisterExpert.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterExpertActivity.class);
            startActivity(intent);
        });

        binding.drawerBtnChangeInfo.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
        });

        binding.drawerBtnChangePassword.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChangePasswordActivity.class);
            startActivity(intent);
        });

        binding.drawerBtnLogout.setOnClickListener(view -> {
            CustomDialog customDialog = new CustomDialog("입력하신 내용을 삭제하시겠습니까?", this, new CustomDialogListener() {
                @Override
                public void onClickOk() {
                    SharedPreferences sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear().apply();
                    startActivity(new Intent(MainPageActivity.this, LoginActivity.class));
                }

                @Override
                public void onClickNo() {

                }
            });
            customDialog.callFunction();
        });

        presenter = new MainPagePresenter(this);
        presenter.getUserProfile(getToken());
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fl_container, fragment).commit();
    }

    @Override
    public void setUserProfile(UserProfile userProfile) {
        if (userProfile.getImage() != null) {
            Bitmap image = BitmapFactory.decodeByteArray(userProfile.getImage(), 0, userProfile.getImage().length);
            binding.mainIvProfile.setImageBitmap(image);
        }

        String name = userProfile.getName() + " 님";
        binding.mainTvName.setText(name);
    }

    @Override
    public void tokenError() {
        presenter.tokenRefresh(getRefreshToken());
    }

    @Override
    public void retryGetUserProfile(Token token) {
        setNewToken(token);
        presenter.getUserProfile(getToken());
    }

    @Override
    public void gotoLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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

    public void openDrawer() {
        binding.mainDl.openDrawer(Gravity.LEFT);
    }

    public void startGraphActivity() {
        Intent intent = new Intent(this, GraphActivity.class);
        startActivity(intent);
    }

    public void startBodyPostActivity() {
        // Todo : 몸변화 리스트 액티비티로 이동하는 코드 추가
    }

    public void getImage(LoadPoseImageListener loadPoseImageListener) {
        this.loadPoseImageListener = loadPoseImageListener;
        Log.d("MainPageActivity", "getImage: 호출");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 123);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                try {
                    assert data != null;
                    Uri uri = data.getData();
                    Log.d("MainPageActivity", "onActivityResult: "+uri.toString());
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    File file = new File(getRealPathFromURI(uri));
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part inputImage = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

                    loadPoseImageListener.load(requestBody, bitmap);
                } catch (Exception ignored) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}