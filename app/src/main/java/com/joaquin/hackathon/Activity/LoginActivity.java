package com.joaquin.hackathon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.joaquin.hackathon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextView forget,signUp;
    private AppCompatButton login;
    private EditText emailEt,password;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initialisation
        forget=findViewById(R.id.forgetPasswordTv);
        login=findViewById(R.id.LoginBtn);
        emailEt=findViewById(R.id.emailEt);
        password=findViewById(R.id.passwordEt);
        signUp=findViewById(R.id.signUpTv);
        auth=FirebaseAuth.getInstance();
        //end

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //forcing light theme

        //Setting click listeners

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Authentication
                if(!emailEt.getText().toString().trim().equals("")){
                    if(!password.getText().toString().trim().equals("")){

                        login.setEnabled(false);

                        //Loading Dialog
                        Dialog d=new Dialog(LoginActivity.this);
                        d.setContentView(R.layout.loading_dialogs);
                        d.getWindow().setBackgroundDrawable(getDrawable(R.drawable.round_bg));
                        d.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        d.setCancelable(false);
                        //end

                        d.show();  //showing dialog untill server process ends
                        //FireBase Auth
                        String emailDataLog = emailEt.getText().toString().trim();
                        String passDataLog = password.getText().toString().trim();
                        auth.signInWithEmailAndPassword(emailDataLog,passDataLog).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    d.dismiss();
                                    Intent log=new Intent(LoginActivity.this, MainDialogActivity.class);
                                    startActivity(log);
                                    finish();

                                }else{
                                    d.dismiss();
                                    Toast.makeText(LoginActivity.this, "Invalid"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    login.setEnabled(true);
                                }
                            }
                        });
                    }else{
                        password.setError("Please Enter Password");
                    }
                }else{
                    emailEt.setError("Please Enter Email");
                }
            }
        });
        //end





        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(it);

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent sign=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(sign);



            }
        });



    }
}