package com.joaquin.hackathon.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.joaquin.hackathon.R;
import com.joaquin.hackathon.Fragments.fragment_account;
import com.joaquin.hackathon.Fragments.fragment_book;
import com.joaquin.hackathon.Fragments.fragment_home;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    private AnimatedBottomBar animatedBottomBar;
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialisation
        frameLayout=findViewById(R.id.fragment_container);
        animatedBottomBar = findViewById(R.id.animatedBottomBar);
         //end
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //forcing light theme




        //setting default Fragment
        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.home, true);
            defaultFragment(new fragment_home());

        }

        //click listeners on bottom navigation items
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {

                switch (newTab.getId()) {
                    case R.id.home:
                        changeFragments(new fragment_home());
                        break;
                    case R.id.courses:
                        changeFragments(new fragment_book());
                        break;
                    case R.id.account:
                        changeFragments(new fragment_account());
                        break;
                }



            }
        });
        //end




    }
    //Fragment transaction manager
    private void defaultFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in,R.anim.fade_out);

        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }

    private void changeFragments(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in,R.anim.fade_out);

        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }
}