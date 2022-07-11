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

public class booksRvAdapter  extends RecyclerView.Adapter<booksRvAdapter.ViewHolder>{

    public static int type;
    ArrayList<String> bannerUrlList= new ArrayList<>();
    ArrayList<String> courseId=new ArrayList<>();
    String s;

    public booksRvAdapter(ArrayList<String> bannerUrlList, ArrayList<String> courseId) {

        this.bannerUrlList = bannerUrlList;
        this.courseId=courseId;
    }

    @NonNull
    @Override
    public booksRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_items,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull booksRvAdapter.ViewHolder holder, int position) {


        //end
        holder.setData(bannerUrlList.get(position));

    }

    @Override
    public int getItemCount() {
        return bannerUrlList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.courseBanner);

        }

        public void setData(String url) {


            Glide.with(itemView.getContext()).load(url).into(imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent course=new Intent(itemView.getContext(), TabedActivity.class);
                    s=courseId.get(getLayoutPosition());
                    course.putExtra("type",s);
                    itemView.getContext().startActivity(course);
                }
            });

        }


    }
}
