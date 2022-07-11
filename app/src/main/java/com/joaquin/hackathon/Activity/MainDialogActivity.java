package com.joaquin.hackathon.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.joaquin.hackathon.R;

public class MainDialogActivity extends AppCompatActivity {

    private ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dialog);
        close=findViewById(R.id.close_dialog_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //forcing light theme

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(MainDialogActivity.this,MainActivity.class);
                startActivity(it);
                finish();
            }
        });

        //Automatic remove if not cancelled
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it=new Intent(MainDialogActivity.this,MainActivity.class);
                startActivity(it);
                finish();
            }
        },5500);



    }
}