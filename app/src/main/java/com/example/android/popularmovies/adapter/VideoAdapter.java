package com.example.android.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ibnumuzzakkir on 7/11/17.
 * Android Engineer
 * SCO Project
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<Video> mVideos;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public VideoAdapter(List<Video> videos, Context context){
        mVideos = videos;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_video, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Video  video = mVideos.get(position);
        String url = mContext.getResources().getString(R.string.youtube_video_thumbnail,video.getKey());
        Log.d(getClass().getName(), url);
        Picasso.with(mContext)
                .load(url)
                .into(holder.mVideoThumbnail);
        holder.mVideoThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + video.getKey()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mVideoThumbnail;
        private View.OnClickListener onClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            mVideoThumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail);
        }
    }
}
