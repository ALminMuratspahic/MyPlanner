package com.almin.myplanner.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.almin.myplanner.MainActivity;
import com.almin.myplanner.R;


public class BlinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blink);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(BlinkActivity.this, MainActivity.class));
            finish();
        }, 3000);

    }
}