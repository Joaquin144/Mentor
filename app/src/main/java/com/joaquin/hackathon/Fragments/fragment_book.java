package com.joaquin.hackathon.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.joaquin.hackathon.Classes.booksRvAdapter;
import com.joaquin.hackathon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class fragment_book extends Fragment {

    ArrayList<String> bannerList= new ArrayList<>();
    ArrayList<String> courseId= new ArrayList<>();
    private RecyclerView booksRV;
    private booksRvAdapter booksAdapter;
    private FirebaseFirestore firebaseFirestore;
    public  static String  BannerUrl;

    private ProgressBar bar;




   
    public fragment_book() {
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
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        //initialisation
        booksRV=view.findViewById(R.id.booksRecyclerView);
        firebaseFirestore=FirebaseFirestore.getInstance();
        bar=view.findViewById(R.id.books_load);
        //end
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //forcing light theme
        return view;
    }
     @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        
        super.onViewCreated(view, savedInstanceState);
       //operational statements
         bannerList.clear();   //to avoid duplicate elements due to multiple clicks

         bar.setVisibility(View.VISIBLE);


         //fetch Starts
         firebaseFirestore.collection("courses").orderBy("orderNumber").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
             @Override
             public void onComplete(@NonNull Task<QuerySnapshot> task) {



                 if(task.isSuccessful()){
                     //to fetch banners and other keys and adding banner url to bannerList
                     for (DocumentSnapshot snap : task.getResult()){
                         BannerUrl=(String)snap.get("imageUrl");
                         courseId.add((String)snap.get("courseId"));
                         bannerList.add(BannerUrl);




                     }
                     //end
                     //setting up adapter and Gridlayout
                     booksAdapter=new booksRvAdapter(bannerList,courseId);
                     GridLayoutManager manager=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                     manager.setOrientation(RecyclerView.VERTICAL);
                     booksRV.setLayoutManager(manager);
                     booksRV.setAdapter(booksAdapter);
                     booksAdapter.notifyDataSetChanged();
                     bar.setVisibility(View.GONE);

                 }else{
                     bar.setVisibility(View.GONE);
                     Toast.makeText(getContext(), "Something Went Wrong! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                 }

             }
         });
    }
}