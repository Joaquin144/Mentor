package com.joaquin.hackathon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.joaquin.hackathon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    private TextView goBack;
    private AppCompatButton resetBtn;
    private EditText oldEmailEt;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        //initialisation
        goBack=findViewById(R.id.backTv);
        resetBtn=findViewById(R.id.resetBtn);
        oldEmailEt=findViewById(R.id.emailEt);
        auth=FirebaseAuth.getInstance();
        //end

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //forcing light theme


        //Reset by sending mail

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!oldEmailEt.getText().toString().trim().equals("")){

                    resetBtn.setEnabled(false);
                    String emailDataToReset=oldEmailEt.getText().toString().trim();
                    //Sending Reset Mail To User
                    auth.sendPasswordResetEmail(emailDataToReset).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                Toast.makeText(ForgetPasswordActivity.this, "Email successfully sent to your registered mail, Dont't Forget to check spam folder", Toast.LENGTH_LONG).show();


                            }else{
                                Toast.makeText(ForgetPasswordActivity.this, "Something Went Wrong"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                resetBtn.setEnabled(true);

                            }


                        }
                    });
                    //end


                }else{

                    oldEmailEt.setError("Please Enter Your Registered Email");
                    resetBtn.setEnabled(true);

                }

            }
        });








        //end


        //click listeners
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(it);
            }
        });



    }
}