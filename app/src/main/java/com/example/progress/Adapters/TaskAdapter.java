package com.example.progress.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.progress.Fragments.TaskListFragment;
import com.example.progress.MainActivity;
import com.example.progress.Models.CheckList;
import com.example.progress.Models.Task;
import com.example.progress.R;
import com.example.progress.TaskList.TaskDeleteActivity;
import com.example.progress.TaskList.TaskSettingsActivity;
import com.parse.DeleteCallback;
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
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public TaskAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task_swipe, parent, false);
        Log.d(TAG, "created viewholder");

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        viewBinderHelper.setOpenOnlyOne(true);
        Task task = tasks.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(task.getName()));
        viewBinderHelper.closeLayout(String.valueOf((task.getName())));
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox cbTask;
        private TextView tvTaskDescription, tvEdit, tvDelete;
        private SwipeRevealLayout swipeRevealLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            cbTask = itemView.findViewById(R.id.cbTaskName);
            tvTaskDescription =  itemView.findViewById(R.id.tvTaskDesc);
            swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);
            tvEdit = itemView.findViewById(R.id.tvEdit);
            tvDelete = itemView.findViewById(R.id.tvDelete);


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

            tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToTaskSettings(task);
                    notifyDataSetChanged();
                }
            });
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToTaskDelete(task);
                    notifyDataSetChanged();
                }


            });
            Log.d(TAG, "Bind Ran \nTaskName:" + task.getName() + "\nDescription: " + task.getDescription());


        }
    }

    private void goToTaskDelete(Task task) {
        Intent i = new Intent(context, TaskDeleteActivity.class);
        i.putExtra("task", task);
        context.startActivity(i);
        return;
    }


    public void goToTaskSettings(Task task){
        Intent i = new Intent(context, TaskSettingsActivity.class);
        i.putExtra("task", task);
        context.startActivity(i);
        return;
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

