package com.joaquin.hackathon.Classes;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.joaquin.hackathon.Activity.DownloadPopupActivity;
import com.joaquin.hackathon.Activity.VideoPlayerActivity;
import com.joaquin.hackathon.R;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{

    List<String> urlList;
    List<String> TitleList;

    public VideoAdapter(List<String> urlList, List<String> titleList) {
        this.urlList = urlList;
        TitleList = titleList;
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_layout,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder holder, int position) {

        holder.setData(TitleList.get(position),R.drawable.play);

    }

    @Override
    public int getItemCount() {
        return TitleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView t;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.videoIcon);
            t=itemView.findViewById(R.id.videoTitle);


            //Listeners to rv items
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog=new Dialog(itemView.getContext());
                    dialog.setContentView(R.layout.option_dialog_layout);
                    dialog.getWindow().setBackgroundDrawable(itemView.getContext().getDrawable(R.drawable.round_bg));
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    AppCompatButton downloadNow=dialog.findViewById(R.id.download_now);
                    AppCompatButton watchNow=dialog.findViewById(R.id.watch_now);
                    dialog.setCancelable(true);
                    dialog.show();
                    //click listeners on dialog options
                    downloadNow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent dIntent=new Intent(itemView.getContext(), DownloadPopupActivity.class);
                            dIntent.putExtra("Title", TitleList.get(getLayoutPosition()));
                            dIntent.putExtra("Link", "https://firebasestorage.googleapis.com/v0/b/hackathon-3505e.appspot.com/o/MentOR.mp4?alt=media&token=9d26b97c-7bcf-48ec-95e4-851d700a3b2f");
                            itemView.getContext().startActivity(dIntent);
                            dialog.dismiss();
                        }

                    });

                    watchNow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent it=new Intent(itemView.getContext(), VideoPlayerActivity.class);
                            it.putExtra("Title",TitleList.get(getLayoutPosition()));
                            it.putExtra("Link",urlList.get(getLayoutPosition()));
                            itemView.getContext().startActivity(it);
                            dialog.dismiss();

                        }
                    });   //end

                }
            });

        }

        private void setData(String title,int icon){
            t.setText(title);
            img.setImageDrawable(itemView.getResources().getDrawable(icon));


        }
    }
}
