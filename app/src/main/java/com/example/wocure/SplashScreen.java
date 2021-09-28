package com.example.wocure;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    SavedPref savedPreferences = SavedPref.getSavedPreferenceInstance();
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        uid = savedPreferences.getData("uid");
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                if(uid !=""){
                    startActivity(new Intent(SplashScreen.this, HomeScreen.class).putExtra(null,false));
                }
                else {
                    Intent i = new Intent(SplashScreen.this, LoginPage.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 1000);

    }
}