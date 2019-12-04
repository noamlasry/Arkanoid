package com.noamls_amirbs.ex2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    private GameView gameView;
    private Sensor mySensor;
    private SensorManager SM;
    final float X = (float) 0;
    Paddle paddle = null;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameView = findViewById(R.id.myViewID);

        // Create our Sensor Manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor Listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);



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
//===============================================================================//

    }


    @Override
    public void onSensorChanged(SensorEvent event)
    {
        float input = round(event.values[1],2);// two digit after the point

        if(input < X-0.5)
        {
            Log.d("t","down from 9.81 ");

            gameView.movePaddle(0);
        }

        else if(input > X+0.5)
        {
            Log.d("tom","up from 9.81 ");
            gameView.movePaddle(1);
        }
        else if(input >= X-0.5 && input <= X+0.5)
        {
            gameView.movePaddle(2);
        }

    }
    public static float round(float d, int decimalPlace)
    {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
