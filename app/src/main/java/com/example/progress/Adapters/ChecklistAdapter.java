package com.example.progress.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progress.Models.CheckList;
import com.example.progress.Models.Task;
import com.example.progress.R;

import org.w3c.dom.Text;

import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {

    private final Context context;
    private final List<CheckList> checkLists;
    public static final String TAG = "Task Adapter";

    public ChecklistAdapter(Context context, List<CheckList> checkLists) {
        this.context = context;
        this.checkLists = checkLists;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_checklist, parent, false);
        Log.d(TAG, "created viewholder");

        return new ViewHolder(view);
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
        private TextView tvChecklistName;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvChecklistName = itemView.findViewById(R.id.tvChecklistName);

        }

        public void bind(CheckList checkList){
            tvChecklistName.setText(checkList.getName());
            Log.d(TAG, "Bind Ran \nListName:" + checkList.getName() + "\nDescription: "+checkList.getDescription());
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
