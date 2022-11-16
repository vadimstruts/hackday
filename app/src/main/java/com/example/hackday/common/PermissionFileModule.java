package com.example.hackday.common;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.hackday.R;

public class PermissionFileModule {

    final AppCompatActivity appCompatActivity;
    public PermissionFileModule(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
    }

    public void checkAndGrantPermission() {
        try {
            if (!checkPermission()) {
                requestPermission();
            }
        } catch (Exception e) {
            Log.e("PermissionFileModule", String.format(appCompatActivity.getApplicationContext().getString(R.string.permission_check_with_error), e));
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(appCompatActivity, Manifest.permission.READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(appCompatActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",appCompatActivity.getPackageName())));
                appCompatActivity.startActivity(intent);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                appCompatActivity.startActivity(intent);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(appCompatActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

}
