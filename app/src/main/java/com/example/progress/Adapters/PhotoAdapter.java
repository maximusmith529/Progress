package com.example.progress.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.progress.AlbumActivity;
import com.example.progress.Models.CheckList;
import com.example.progress.Models.Photo;
import com.example.progress.R;
import com.parse.ParseFile;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>  {

    private final Context context;
    private final List<Photo> photos;
    public static final String TAG = "Album Adapter";

    public PhotoAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false);
        Log.d(TAG, "created viewholder");

        return new PhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.bind(photo);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivPhoto;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);

        }

        public void bind(Photo photo) {

            ParseFile image = photo.getKeyImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivPhoto);

            }
        }

    }
    // Clean all elements of the recycler
    public void clear() {
        photos.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Photo> list) {
        photos.addAll(list);
        notifyDataSetChanged();
    }
}