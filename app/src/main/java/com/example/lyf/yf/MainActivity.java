package com.example.lyf.yf;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lyf.yflibrary.Permission;
import com.example.lyf.yflibrary.PermissionResult;

public class MainActivity extends AppCompatActivity {

    private String[] REQUEST_PERMISSIONS = new String[]{Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Permission.checkPermisson(this, REQUEST_PERMISSIONS, new PermissionResult() {

            @Override
            public void success() {

            }

            @Override
            public void fail() {

            }
        });
    }



}
