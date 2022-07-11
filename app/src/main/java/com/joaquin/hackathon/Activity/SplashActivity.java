package com.joaquin.hackathon.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.joaquin.hackathon.R;
import com.google.firebase.auth.FirebaseAuth;

import in.codeshuffle.typewriterview.TypeWriterView;

public class SplashActivity extends AppCompatActivity {
    private AppCompatButton signUp;
    private TextView login;
    FirebaseAuth firebaseAuth;
    private TypeWriterView text;
    private  NetworkInfo networkInfo;
    private  ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        signUp=findViewById(R.id.SplashSignUpBtn);
        login=findViewById(R.id.SplashLogIn);
        text=findViewById(R.id.Splashtitle);
        firebaseAuth=FirebaseAuth.getInstance();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //forcing light theme



        //setting up typeWriterText
        text.setDelay(2);
        text.setWithMusic(false);
        text.animateText("Want to learn new Skills?");
        //end



        //Click Listeners
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInternet()){
                    Intent it=new Intent(SplashActivity.this,SignUpActivity.class);
                    startActivity(it);
                    finish();
                }else{
                    Intent internetPage=new Intent(SplashActivity.this,internetActivity.class);
                    startActivity(internetPage);finish();

                }






            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(firebaseAuth.getCurrentUser()!=null){
                    if (checkInternet()){
                        Intent it=new Intent(SplashActivity.this,MainDialogActivity.class);
                        startActivity(it);
                        finish();
                    }else{
                        Intent internetPage=new Intent(SplashActivity.this,internetActivity.class);
                        startActivity(internetPage);finish();

                    }


                }else{
                    if(checkInternet()){
                        Intent it=new Intent(SplashActivity.this,LoginActivity.class);
                        startActivity(it);
                        finish();

                    }else{
                        Intent internetPage=new Intent(SplashActivity.this,internetActivity.class);
                        startActivity(internetPage);finish();

                    }




                }






            }
        });



    }
    private  boolean checkInternet(){


        //Checking Internet Connection
        connectivityManager=(ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            return true;
        }
        return false;

    }

    }
