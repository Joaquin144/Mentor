package com.joaquin.hackathon.Classes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerAdapter extends FragmentStateAdapter {

    public static List<String> tabTitleList = new ArrayList<>();
    public static List<Fragment> tabFragmentList = new ArrayList<>();

    public ViewpagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return tabFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return tabFragmentList.size();
    }

    public void addFragments(Fragment fragment,String name){

         tabFragmentList.add(fragment);
         tabTitleList.add(name);

    }
}
