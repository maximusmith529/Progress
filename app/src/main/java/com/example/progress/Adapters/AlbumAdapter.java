package com.example.progress.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.progress.Models.CheckList;
import com.example.progress.R;
import com.parse.ParseFile;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>  {

    private final Context context;
    private final List<CheckList> checkLists;
    public static final String TAG = "Album Adapter";

    public AlbumAdapter(Context context, List<CheckList> checkLists) {
        this.context = context;
        this.checkLists = checkLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false);
        Log.d(TAG, "created viewholder");

        return new AlbumAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckList checkList = checkLists.get(position);
        holder.bind(checkList);
    }

    @Override
    public int getItemCount() {
        return checkLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvAlbumName;
        private ImageView ivThumbnail;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvAlbumName = itemView.findViewById(R.id.tvAlbumName);
            ivThumbnail = itemView.findViewById(R.id.ivAlbumThumbnail);

        }

        public void bind(CheckList checkList){
            tvAlbumName.setText(checkList.getName());
            Log.d(TAG, "Bind Ran \nListName:" + checkList.getName() + "\nDescription: "+checkList.getDescription());
            ParseFile image = checkList.getKeyThumbnail();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivThumbnail);
            }
        }

    }
    // Clean all elements of the recycler
    public void clear() {
        checkLists.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<CheckList> list) {
        checkLists.addAll(list);
        notifyDataSetChanged();
    }
}
