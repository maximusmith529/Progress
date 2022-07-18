package com.example.progress.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progress.Models.CheckList;
import com.example.progress.Models.Task;
import com.example.progress.R;
import com.example.progress.TaskList.ChecklistSettingsActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {

    private final Context context;
    private final List<CheckList> checkLists;
    public static final String TAG = "Checklist Adapter";

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
        private Switch swtIsActive;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvChecklistName = itemView.findViewById(R.id.tvChecklistName);
            swtIsActive = itemView.findViewById(R.id.swtIsActive);

        }

        public void bind(CheckList checkList){
            tvChecklistName.setText(checkList.getName());
            Log.d(TAG, "Bind Ran \nListName:" + checkList.getName() + "\nDescription: "+checkList.getDescription());
            if(checkList.getIsActive()) {
                swtIsActive.setChecked(true);
                swtIsActive.setText("Active");
            }
            else {
                swtIsActive.setText("Inactive");
                swtIsActive.setChecked(false);
            }

            swtIsActive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: finish setActive in checkLists
                    // get only checklists
                    ParseQuery<CheckList> query = ParseQuery.getQuery(CheckList.class);
                    // only from user
                    query.include(CheckList.KEY_USER);
                    query.whereEqualTo(CheckList.KEY_USER, ParseUser.getCurrentUser());
                    query.orderByDescending("created_at");
                    query.findInBackground(new FindCallback<CheckList>() {
                        @Override
                        public void done(List<CheckList> objects, ParseException e) {
                            if (e != null) {
                                Log.e(TAG, "Issue with getting lists", e);
                                return;
                            }
                            for(CheckList t : objects) {
                                Log.i(TAG, "List Name = " + t.getName());
                                if(t.getIsActive()) {
                                    t.setIsActive(false);
                                    t.saveData();
                                }
                            }
                        }
                    });
                    checkList.setIsActive(true);
                    checkList.saveData();
                    notifyDataSetChanged();


                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ChecklistSettingsActivity.class);
                    i.putExtra("checklist", checkList);
                    context.startActivity(i);
                    return;
                }
            });

        }
        public void refresh(){
            for(CheckList c: checkLists)
                bind(c);
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
