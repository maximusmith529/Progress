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

import com.example.progress.Models.Task;
import com.example.progress.R;

import org.w3c.dom.Text;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private final Context context;
    private final List<Task> tasks;
    public static final String TAG = "Task Adapter";

    public TaskAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        Log.d(TAG, "created viewholder");

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox cbTask;
        private TextView tvTaskDescription;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            cbTask = itemView.findViewById(R.id.cbTaskName);
            tvTaskDescription =  itemView.findViewById(R.id.tvTaskDesc);


        }

        public void bind(Task task){
            cbTask.setText(task.getName());
            if(!task.getDescription().isEmpty())
                tvTaskDescription.setText(task.getDescription());

            //TODO: implement checklist code
            cbTask.setText(task.getName());
            cbTask.setChecked(task.isFinished());
            tvTaskDescription.setText(task.getDescription());

            Log.d(TAG, "Bind Ran \nTaskName:" + task.getName() + "\nDescription: "+task.getDescription());
        }

    }
}
