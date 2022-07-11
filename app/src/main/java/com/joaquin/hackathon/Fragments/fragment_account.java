package com.joaquin.hackathon.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.joaquin.hackathon.Activity.AccountSettingActivity;
import com.joaquin.hackathon.Activity.LoginActivity;
import com.joaquin.hackathon.Activity.WhatsappActivity;
import com.joaquin.hackathon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.Nullable;


public class fragment_account extends Fragment {

    private LinearLayout acctSettings,share,about,logout;
    private FirebaseFirestore firebaseFirestore;
    private TextView name,email;



    public fragment_account() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        //initialisation
        acctSettings=view.findViewById(R.id.AccountLinear);
        share=view.findViewById(R.id.shareLinear);
        about=view.findViewById(R.id.aboutLinear);
        logout=view.findViewById(R.id.logoutLinear);
        name=view.findViewById(R.id.userAcctName);
        email=view.findViewById(R.id.userAcctEmail);
        firebaseFirestore=FirebaseFirestore.getInstance();
        //end
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //forcing light theme
        return view;
    }
    
     @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        
        super.onViewCreated(view, savedInstanceState);
        //setting texts on name and email
         firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                 if(task.isSuccessful()){

                     DocumentSnapshot snap=task.getResult();
                     name.setText((String)snap.get("name"));
                     email.setText((String)snap.get("email"));

                 }else{
                     Toast.makeText(getContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                 }
             }
         });
         //end

         //Click Listeners
         acctSettings.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 Intent settings=new Intent(getContext(), AccountSettingActivity.class);
                 getActivity().startActivity(settings);



             }
         });
         share.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 Intent sendIntent = new Intent();
                 sendIntent.setAction(Intent.ACTION_SEND);
                 sendIntent.putExtra(Intent.EXTRA_TEXT,
                         "Hey check out my app at: https://github.com/RahulSoni0/practicerepo");
                 sendIntent.setType("text/plain");
                 startActivity(sendIntent);


             }
         });

         about.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getContext(), WhatsappActivity.class);
                 getActivity().startActivity(intent);
             }
         });

         logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 FirebaseAuth.getInstance().signOut();
                 Intent it=new Intent(getContext(), LoginActivity.class);
                 startActivity(it);
                 getActivity().finish();
             }
         });
         //End
     }
}
