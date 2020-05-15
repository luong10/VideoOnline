package com.example.mywatch.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywatch.PlayVideoActivity;
import com.example.mywatch.R;
import com.example.mywatch.VideoYouTube;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoYouTubeAdapterTren extends RecyclerView.Adapter<VideoYouTubeAdapterTren.ReViewHolder> {
    private Context context;
    private List<VideoYouTube> videoYouTubes;

    public VideoYouTubeAdapterTren(Context context, List<VideoYouTube> videoYouTubes) {
        this.context = context;
        this.videoYouTubes = videoYouTubes;
    }

    @NonNull
    @Override
    public ReViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_video_youtube_trend,parent,false);
        ReViewHolder holder = new ReViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReViewHolder holder, final int position) {
        Picasso.with(context).load(videoYouTubes.get(position).getThumbnails()).into(holder.img);
        holder.txttitle.setText(videoYouTubes.get(position).getTitle());
        holder.date.setText(videoYouTubes.get(position).getPublishedAt());
        holder.channelTitles.setText(videoYouTubes.get(position).getChannelTitle());
        holder.myCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayVideoActivity.class);
                intent.putExtra("idVideoYouTube",videoYouTubes.get(position).getIdvideo());
                intent.putExtra("nameVideoYouTube",videoYouTubes.get(position).getTitle());
                intent.putExtra("ngayVideoYouTube",videoYouTubes.get(position).getPublishedAt());
                intent.putExtra("kenhVideoYouTube",videoYouTubes.get(position).getChannelTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoYouTubes.size();
    }

//    private static OnItemClickListener listener;
//
//    public interface OnItemClickListener {
//        void onItemClick(View itemView, int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }


    public class ReViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txttitle;
        private TextView date;
        private TextView channelTitles;
        private CardView myCardView;

        public ReViewHolder(@NonNull final View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.ivthumbnails1);
            txttitle = (TextView) itemView.findViewById(R.id.tvtitle1);
            date = (TextView) itemView.findViewById(R.id.publishedAt1);
            channelTitles = (TextView)itemView.findViewById(R.id.channelTitle1);
            myCardView = (CardView)itemView.findViewById(R.id.myCard);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (listener != null)
//                        listener.onItemClick(itemView, getLayoutPosition());
//                }
//            });
        }
    }
}
