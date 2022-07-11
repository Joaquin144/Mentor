package com.joaquin.hackathon.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.joaquin.hackathon.Classes.homePosterAdapter;
import com.joaquin.hackathon.Classes.home_adapter;
import com.joaquin.hackathon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import in.codeshuffle.typewriterview.TypeWriterView;
import nl.joery.animatedbottombar.AnimatedBottomBar;


public class fragment_home extends Fragment {

    private FrameLayout ParentFrameLayout;
    private TextView explore;
    private home_adapter homeAdapter;
    private AnimatedBottomBar BottomBar;
    private RecyclerView homeRv;
    ArrayList<String> homeCourseurl=new ArrayList<>();
    ArrayList<String> id=new ArrayList<>();
    private ProgressBar bar;
    private ViewPager slider;
    ArrayList<String> homePosterList=new ArrayList<>();
    private homePosterAdapter posterAdapter;
    ArrayList<String> arrangedPosterList=new ArrayList<>();
    int currentPage;
    final static int DELAY_TIME=2000;
    final static int PERIOD_TIME=2000;
    Timer timer;
    private  TextView slidingText;
    String t="Welcome to the best application ever which is here to provide best courses with fully experienced instructors. Here we also provide chatBots and various other best support systems which will help user to communicate with us.";
    private TypeWriterView name;
//    private FloatingActionButton fabBtn;
    DatabaseReference realTime;
    ArrayList<String> duplicateId=new ArrayList<>();
    FirebaseAuth auth;
    FirebaseFirestore store;
   
    public fragment_home() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //initialise views
        homeRv=view.findViewById(R.id.homeRecyclerView);
        explore=view.findViewById(R.id.exploreTv);
        slider=view.findViewById(R.id.homeVp);
        slidingText=view.findViewById(R.id.slidingText);
        name=view.findViewById(R.id.userName);
        auth=FirebaseAuth.getInstance();
        realTime=FirebaseDatabase.getInstance().getReference();
        store=FirebaseFirestore.getInstance();
//        fabBtn=view.findViewById(R.id.chatBot);
        bar=view.findViewById(R.id.home_load);
        //end
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //forcing light theme
        return view;

    }
     @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

         super.onViewCreated(view, savedInstanceState);
         //initialising the main Activity Frame
         ParentFrameLayout=getActivity().findViewById(R.id.fragment_container);
         BottomBar=getActivity().findViewById(R.id.animatedBottomBar);

         //text sliding implementation
         slidingText.setText(t);
         slidingText.setSelected(true);
         //end

         //operational statements
         homeCourseurl.clear();  //to avoid duplicate items
         homePosterList.clear();  //to avoid duplicate items
         id.clear();  //to avoid duplicate items


         //Fetching user name
         store.collection("users").document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                 bar.setVisibility(View.VISIBLE);
                   DocumentSnapshot snapshot = task.getResult();
                   String fetchedName=(String)snapshot.get("name");
                   fetchedName=fetchedName.trim();
                 //setting up typeWriterText
                 name.setDelay(2);
                 name.setWithMusic(false);
                 if(fetchedName.indexOf(" ")!=-1){

                     name.animateText("Hello, "+fetchedName.substring(0,fetchedName.indexOf(" ")));

                 }else{

                     name.animateText("Hello, "+fetchedName);


                 }

                 bar.setVisibility(View.GONE);


             }
         });
         //end

//         fabBtn.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//
//                 //todo:chatBot
//                 Toast.makeText(getContext(), "Under development", Toast.LENGTH_SHORT).show();
//                 Intent intent = new Intent(getContext(),RCSActivity.class);
//                 startActivity(intent);
//             }
//         });

         realTime.child("trending").addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                     bar.setVisibility(View.VISIBLE);


                     id.add(snapshot.child("a").getValue(String.class));
                     id.add(snapshot.child("b").getValue(String.class));
                     id.add(snapshot.child("c").getValue(String.class));
                     id.add(snapshot.child("d").getValue(String.class));
                     id.add(snapshot.child("e").getValue(String.class));
                     id.add(snapshot.child("f").getValue(String.class));




                 Log.d("@@",id.toString());  //perfect

                     store.collection("courses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                         @Override
                         public void onComplete(@NonNull Task<QuerySnapshot> task) {



                             for (int i = 0; i <id.size(); i++) {

                                 for (DocumentSnapshot snap:task.getResult()){



                                     if(id.get(i).equals(snap.get("courseId"))){
                                         homeCourseurl.add((String)snap.get("homeLink"));


                                     }
                                 }
                             }


                             //Setting Adapter and Grid Layout
                             homeAdapter=new home_adapter(homeCourseurl,id);
                             GridLayoutManager manager=new GridLayoutManager(getContext(),2);
                             manager.setOrientation(RecyclerView.VERTICAL);
                             homeRv.setLayoutManager(manager);
                             homeRv.setAdapter(homeAdapter);
                             homeAdapter.notifyDataSetChanged();
                             //end
                             bar.setVisibility(View.GONE);
                         }
                     });




             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

                 Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();

             }
         });


         //hardcoded url's for sliders
         homePosterList.add("https://firebasestorage.googleapis.com/v0/b/hackathon-3505e.appspot.com/o/sliders%2Ffirst.jpg?alt=media&token=62c577bd-355f-4620-8ca1-2aa8b877b4ae");
         homePosterList.add("https://firebasestorage.googleapis.com/v0/b/hackathon-3505e.appspot.com/o/sliders%2Fsecond.jpg?alt=media&token=3011b7ee-f4fd-49f3-ac04-2432df3e9240");
         homePosterList.add("https://firebasestorage.googleapis.com/v0/b/hackathon-3505e.appspot.com/o/sliders%2Fthird.jpg?alt=media&token=ec64d677-a963-4fa7-bb1f-cb9bf610601c");
         //end

         setPosters(homePosterList); //calling poster slider function

         //explore Button Onclick Changing Fragment
         explore.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                 fragmentTransaction.setCustomAnimations(R.anim.slide_in,R.anim.fade_out);
                 fragmentTransaction.replace(ParentFrameLayout.getId(),new fragment_book());
                 fragmentTransaction.commit();
                 BottomBar.selectTabById(R.id.courses,true);

             }
         });
        //end

    }

    private  void setPosters(ArrayList<String> l){

        bar.setVisibility(View.VISIBLE);

        if(timer!=null){
            timer.cancel();

        }

        currentPage=1;  //-->first url of main list
        //applying logic to make sliders infinite so modifying list
                for (int i=0;i<l.size();i++){
                    arrangedPosterList.add(l.get(i));
                }

                arrangedPosterList.add(0,l.get(l.size()-1));
                arrangedPosterList.add(l.get(0));

        //setting Adapter for poster Slider
        posterAdapter=new homePosterAdapter(arrangedPosterList);
        slider.setAdapter(posterAdapter);
        slider.setPageMargin(56);

        slider.setCurrentItem(currentPage);
        //end

        autoSliding(arrangedPosterList);

        ViewPager.OnPageChangeListener listener=new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if(state==ViewPager.SCROLL_STATE_IDLE){

                    loopingInfinitely(arrangedPosterList);

                }

            }
        };

        slider.addOnPageChangeListener(listener);
        bar.setVisibility(View.GONE);

        slider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                loopingInfinitely(arrangedPosterList);
                stopSlidingOnTouch();

                if(motionEvent.getAction()==MotionEvent.ACTION_UP){

                    autoSliding(arrangedPosterList);

                }
                return false;
            }
        });

    }

    private void loopingInfinitely(ArrayList<String> l){

        if(currentPage==l.size()-1){

            currentPage=1;
            slider.setCurrentItem(currentPage,false);

        }else if(currentPage==0){
            currentPage=l.size()-2;
            slider.setCurrentItem(currentPage,false);
        }

    }

    private void autoSliding(ArrayList<String> l){

        final Handler handler=new Handler();
        final Runnable run=new Runnable() {
            @Override
            public void run() {

                if(currentPage>=l.size()){

                    currentPage=0;

                }

                slider.setCurrentItem(currentPage++,true);

            }
        };

        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                handler.post(run);

            }
        },DELAY_TIME,PERIOD_TIME);

    }

    private void stopSlidingOnTouch(){

        timer.cancel();

    }




}
