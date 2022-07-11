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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AccountSettingActivity extends AppCompatActivity {
    private EditText nameEt, passwordEt, confirmPasswordEt, oldPasswordEt;
    private TextView emailEt;
    private FirebaseAuth auth;
    private AppCompatButton saveChanges;
    private FirebaseFirestore firestore;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        nameEt = findViewById(R.id.nameEt);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        oldPasswordEt = findViewById(R.id.oldPasswordEt);
        confirmPasswordEt = findViewById(R.id.confirmPasswordEt);
        saveChanges = findViewById(R.id.SignUpBtn);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String email = firebaseUser.getEmail();

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //forcing light theme

        //statements
        firestore.collection("users").document(auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    String name = documentSnapshot.getString("name");
                    emailEt.setText(email);
                    nameEt.setText(name);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(AccountSettingActivity.this, "Some error Occurred! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(AccountSettingActivity.this);
                dialog.setContentView(R.layout.loading_dialogs);
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.round_bg));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.show();

                if (passwordEt.getText().toString().equals("") || oldPasswordEt.getText().toString().equals("") || confirmPasswordEt.getText().toString().equals("")  ) {

                    //to update only name
                    Map<String, Object> basicUpdate = new HashMap<>();
                    String newName = nameEt.getText().toString().trim();
                    basicUpdate.put("name", newName);
                    basicUpdate.put("email",email);
                    firestore.collection("users").document(auth.getUid()).update(basicUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            if (task.isSuccessful()) {
                                Toast.makeText(AccountSettingActivity.this, "Account Updated Successfully!", Toast.LENGTH_SHORT).show();
                                finish();


                            } else {
                                Toast.makeText(AccountSettingActivity.this, "Some error Occurred :(, Try Again"+task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }

                            dialog.dismiss();

                        }
                    });
                    //to update name and password

                } else {

                    if (!oldPasswordEt.getText().toString().trim().equals("")) {
                        if (passwordEt.getText().toString().trim().equals(confirmPasswordEt.getText().toString().trim()) && passwordEt.getText().toString().trim().length() >= 6) {


                            String newName = nameEt.getText().toString().trim();
                            String newPassword = passwordEt.getText().toString().trim();
                            AuthCredential credentials = EmailAuthProvider.getCredential(email, oldPasswordEt.getText().toString().trim());
                            firebaseUser.reauthenticate(credentials).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    Map<String, Object> updateWithPassword = new HashMap<>();
                                                    String newName = nameEt.getText().toString().trim();
                                                    updateWithPassword.put("name", newName);
                                                    firestore.collection("users").document(auth.getUid()).update(updateWithPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(AccountSettingActivity.this, "Account Updated Successfully!", Toast.LENGTH_SHORT).show();
                                                                Intent it=new Intent(AccountSettingActivity.this,MainActivity.class);
                                                                startActivity(it);
                                                                finish();


                                                            } else {
                                                                Toast.makeText(AccountSettingActivity.this, "Some error Occurred :(, Try Again", Toast.LENGTH_LONG).show();

                                                            }

                                                            dialog.dismiss();

                                                        }
                                                    });


                                                } else {

                                                    dialog.dismiss();
                                                    Toast.makeText(AccountSettingActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                                }

                                            }
                                        });


                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(AccountSettingActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }
                            });


                        } else {
                            dialog.dismiss();
                            Toast.makeText(AccountSettingActivity.this, "Please enter a valid password", Toast.LENGTH_LONG).show();

                        }


                    } else {
                        dialog.dismiss();
                        Toast.makeText(AccountSettingActivity.this, "Please enter your old password", Toast.LENGTH_LONG).show();


                    }
                }


            }
        });

    }
}
