package com.joaquin.hackathon.Classes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joaquin.hackathon.Activity.DownloadPopupActivity;
import com.joaquin.hackathon.R;

import java.util.List;

public class ebooksAdapter extends RecyclerView.Adapter<ebooksAdapter.ViewHolder> {

    private List<String> titleList,linkList;


    public ebooksAdapter(List<String> titleList, List<String> linkList) {
        this.titleList = titleList;
        this.linkList = linkList;
    }

    @NonNull
    @Override
    public ebooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ebooks_layout,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ebooksAdapter.ViewHolder holder, int position) {

        holder.setData(titleList.get(position),R.drawable.download_icon);

    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView i;
        private TextView t;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            i=itemView.findViewById(R.id.iconIm);
            t=itemView.findViewById(R.id.titleTv);
            //listeners to recycler views
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent download=new Intent(itemView.getContext(), DownloadPopupActivity.class);
                    download.putExtra("Title",titleList.get(getLayoutPosition()));
                    download.putExtra("Link",linkList.get(getLayoutPosition()));
                    itemView.getContext().startActivity(download);
                }
            });
        }

        private void setData(String title,int icon){

            i.setImageDrawable(itemView.getResources().getDrawable(icon));
            t.setText(title);


        }
    }
}
