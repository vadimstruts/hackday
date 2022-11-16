package com.example.hackday.common;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.hackday.R;
import com.example.hackday.common.asynctask.IParametrizedCallback;

public class PermissionFileModule {

    public final static int PermissionRequestCode = 1231122;

    final AppCompatActivity appCompatActivity;
    final ActivityResultLauncher<Intent> someActivityResultLauncher;
    IParametrizedCallback<Void> callBack = null;
    boolean isPermissionRequestInProgress = false;
    public PermissionFileModule(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;

        someActivityResultLauncher = appCompatActivity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (null != callBack && checkPermission()) {
                            isPermissionRequestInProgress = false;
                            callBack.Call(null);
                        }
                    }
                });
    }

    public void checkAndGrantPermission(IParametrizedCallback<Void> callBack) {
        this.callBack = callBack;

        try {
            if (!checkPermission()) {
                requestPermission();
            } else {
                if(null != this.callBack)
                    this.callBack.Call(null);
            }
        } catch (Exception e) {
            Log.e("PermissionFileModule", String.format(appCompatActivity.getApplicationContext().getString(R.string.permission_check_with_error), e));
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == PermissionFileModule.PermissionRequestCode) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        Log.d("PermissionFileModule", "Permission granted");
                        isPermissionRequestInProgress = false;
                        if(null != this.callBack)
                            this.callBack.Call(null);
                    } else {
                        requestPermission();
                    }
                }
            }
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
        if(isPermissionRequestInProgress)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",appCompatActivity.getPackageName())));
                someActivityResultLauncher.launch(intent);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                someActivityResultLauncher.launch(intent);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(appCompatActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionFileModule.PermissionRequestCode);
        }

        isPermissionRequestInProgress = true;
    }

}
