package com.example.keepocket2.view.Session;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.keepocket2.R;
import com.example.keepocket2.view.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LogoActivity extends AppCompatActivity{
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(LogoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);

    }
}
