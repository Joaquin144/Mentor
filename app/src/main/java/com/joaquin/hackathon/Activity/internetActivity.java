package com.joaquin.hackathon.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.joaquin.hackathon.R;

public class internetActivity extends AppCompatActivity {

    private TextView retry;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);
        retry=findViewById(R.id.retryTv);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //forcing light theme

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(checkInternetAgain()){
                    Intent it=new Intent(internetActivity.this,SplashActivity.class);
                    startActivity(it);finish();
                }else{
                    Toast.makeText(internetActivity.this, "Sorry, No internet!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private boolean checkInternetAgain(){

        //Checking Internet Connection
        connectivityManager=(ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){

            return  true;

        }
        return false;
    }
}