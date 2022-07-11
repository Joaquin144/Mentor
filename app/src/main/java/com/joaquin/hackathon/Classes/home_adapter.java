package com.joaquin.hackathon.Classes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.joaquin.hackathon.Activity.TabedActivity;
import com.joaquin.hackathon.R;

import java.util.ArrayList;



public class home_adapter extends RecyclerView.Adapter<home_adapter.ViewHolder>{

    ArrayList<String> list;
    ArrayList<String> id;

    public home_adapter(ArrayList<String> list,ArrayList<String> id) {
        this.list = list;
        this.id = id;
    }



    @NonNull
    @Override
    public home_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_course_items, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull home_adapter.ViewHolder holder, int position) {

        holder.setImage(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.home_rv_image);


        }

        private void  setImage(String url){

            Glide.with(itemView.getContext()).load(url).into(imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent it=new Intent(itemView.getContext(), TabedActivity.class);
                    it.putExtra("type",id.get(getLayoutPosition()));
                    itemView.getContext().startActivity(it);


                }
            });

        }
    }
}
