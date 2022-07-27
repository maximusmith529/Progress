package com.example.progress.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progress.Models.CheckList;
import com.example.progress.Models.Task;
import com.example.progress.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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
        private ConstraintLayout itemLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            cbTask = itemView.findViewById(R.id.cbTaskName);
            tvTaskDescription =  itemView.findViewById(R.id.tvTaskDesc);
            itemLayout = itemView.findViewById(R.id.itemLayout);

        }

        public void bind(Task task) {
            cbTask.setText(task.getName());
            if (task.getDescription() != null)
                tvTaskDescription.setText(task.getDescription());
            else {
                tvTaskDescription.setVisibility(View.GONE);

            }

            cbTask.setText(task.getName());
            cbTask.setChecked(task.isFinished());
            //tvTaskDescription.setText(task.getDescription());
            cbTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                    task.setFinished(isChecked);
                    task.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                Log.e(TAG, "Error while saving task data: " + task.getName() + task.isFinished());
                            }
                            notifyDataSetChanged();
                        }
                    });

                }
            });

            Log.d(TAG, "Bind Ran \nTaskName:" + task.getName() + "\nDescription: " + task.getDescription());


        }
    }
    // Clean all elements of the recycler
    public void clear() {
        tasks.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Task> list) {
        tasks.addAll(list);
        notifyDataSetChanged();
    }
}

