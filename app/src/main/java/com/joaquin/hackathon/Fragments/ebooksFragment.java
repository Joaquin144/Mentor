package com.joaquin.hackathon.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.joaquin.hackathon.Activity.TabedActivity;
import com.joaquin.hackathon.Classes.ebooksAdapter;
import com.joaquin.hackathon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class ebooksFragment extends Fragment {

    private List<String> ebooksTitle=new ArrayList<>();
    private List<String> ebooksLink=new ArrayList<>();
    private RecyclerView ebooksRv;
    private ebooksAdapter ebooksAdapter;
    String t;
    FirebaseFirestore store;
    private ProgressBar bar;




    public ebooksFragment() {
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
        View view = inflater.inflate(R.layout.fragment_ebooks, container, false);
        //initialisation
        bar=view.findViewById(R.id.loading);
        ebooksRv=view.findViewById(R.id.ebooksRv);
        t= TabedActivity.t;
        //end
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //forcing light theme
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //fetch from db

        store=FirebaseFirestore.getInstance();
        store.collection("courses").document(t).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                bar.setVisibility(View.VISIBLE);
                DocumentSnapshot snapshot=task.getResult();

                if(task.isSuccessful()){


                    ebooksTitle=(ArrayList<String>)snapshot.get("ebookTitle");
                    ebooksLink=(ArrayList<String>)snapshot.get("ebookLink");



                    if(ebooksTitle.size()==1 && ebooksTitle.get(0).equals("")){

                        ebooksTitle.clear();
                        ebooksLink.clear();

                    }
                    //setting Linear layout manager to rv
                    LinearLayoutManager manager=new LinearLayoutManager(getContext());
                    manager.setOrientation(RecyclerView.VERTICAL);
                    ebooksRv.setLayoutManager(manager);
                    //end

                    //setting Adapter
                    ebooksAdapter=new ebooksAdapter(ebooksTitle,ebooksLink);
                    ebooksRv.setAdapter(ebooksAdapter);
                    ebooksAdapter.notifyDataSetChanged();
                    //end
                    bar.setVisibility(View.GONE);

                }else{

                    Toast.makeText(getContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    bar.setVisibility(View.GONE);
                }
            }
        });




    }
}