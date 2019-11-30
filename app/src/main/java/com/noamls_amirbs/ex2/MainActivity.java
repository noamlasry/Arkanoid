package com.noamls_amirbs.ex2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// ===============  screen definition =============================================================//
        // set FULL SCREEN - need to hide Status & Action Bar
        // ===================================================
        // Hide the Activity Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide the Activity Action Bar
        getSupportActionBar().hide();
        // set SCREEN ORIENTATION
        // ===================================================
        // set Activity(screen) Orientation to LANDSCAPE
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
// ============================================================================================//





    }
}
