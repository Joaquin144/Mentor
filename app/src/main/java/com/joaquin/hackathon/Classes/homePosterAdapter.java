package com.joaquin.hackathon.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.joaquin.hackathon.R;

import java.util.ArrayList;

public class homePosterAdapter extends PagerAdapter {

    private ArrayList<String> list=new ArrayList<>();

    public homePosterAdapter(ArrayList<String> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.poster_slider_layout,container,false);
        ImageView posterImage=view.findViewById(R.id.posterImageContainer);
        Glide.with(container.getContext()).load(list.get(position)).into(posterImage);
        container.addView(view,0);
        return view;

    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}
