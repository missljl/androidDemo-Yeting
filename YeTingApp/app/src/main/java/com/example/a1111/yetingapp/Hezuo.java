package com.example.a1111.yetingapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Hezuo extends AppCompatActivity {


    private Button backbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hezuo);

        backbtn = (Button) findViewById(R.id.backbtn11);


    }

    public void click(View view){
        finish();
        overridePendingTransition(R.layout.leftout, R.layout.left_outtow);

    }

}
