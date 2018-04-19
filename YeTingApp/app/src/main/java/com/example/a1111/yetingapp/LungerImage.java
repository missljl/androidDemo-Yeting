package com.example.a1111.yetingapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import  android.os.Handler;
import android.content.Intent;
import android.view.WindowManager;

public class LungerImage extends AppCompatActivity {

    private final  int SPLASH_DISPLAY_LENGHT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);

        setContentView(R.layout.activity_lunger_image);

new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {

        Intent mainIntent = new Intent(LungerImage.this,MainActivity.class);
        LungerImage.this.startActivity(mainIntent);

        LungerImage.this.finish();

    }
},SPLASH_DISPLAY_LENGHT);


    }

}
