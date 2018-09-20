package com.example.lenovo.te_ker;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lenovo.te_ker.data.AppPreference;

public class MainActivity extends AppCompatActivity {


    private static int SPLASH_TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                ifLoggedIn();
            }
        },SPLASH_TIME_OUT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                ifLoggedIn();
            }
        },SPLASH_TIME_OUT);
    }

    private void ifLoggedIn() {
        boolean isLoggedIn = AppPreference.getLogin(this);
        if(isLoggedIn == false) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}