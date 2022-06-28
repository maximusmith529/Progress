package com.example.progress.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.progress.Adapters.ChecklistAdapter;
import com.example.progress.Adapters.TaskAdapter;
import com.example.progress.Models.CheckList;
import com.example.progress.Models.Task;
import com.example.progress.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TaskListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String TAG = "TaskList Fragment";
    private RecyclerView rvTasks;
    private TextView tvChecklistTitle;
    protected TaskAdapter adapter;
    protected ChecklistAdapter checklistAdapter;
    protected List<Task> tasks;
    protected List<CheckList> checklists;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChecklistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskListFragment newInstance(String param1, String param2) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TaskListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasklist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTasks = view.findViewById(R.id.rvTasks);
        tvChecklistTitle = view.findViewById(R.id.tvChecklistName);
        tasks = new ArrayList<>();
        adapter = new TaskAdapter(getContext(), tasks);
        //not used, yet TODO: Replace textView with adapter
        checklistAdapter = new ChecklistAdapter(getContext(), checklists);


        // set the adapter on the recycler view
        rvTasks.setAdapter(adapter);
        // set the layout manager on the recycler view
        rvTasks.setLayoutManager(new LinearLayoutManager(getContext()));


        queryActiveListsForCurrentUser();

    }

    private void queryActiveListsForCurrentUser(){
        // get only checklists
        ParseQuery<CheckList> query = ParseQuery.getQuery(CheckList.class);
        // only from user
        query.include(CheckList.KEY_USER);
        query.whereEqualTo(CheckList.KEY_USER, ParseUser.getCurrentUser());
        query.whereEqualTo(CheckList.isActive, true);
        query.orderByDescending("created_at");
        query.findInBackground(new FindCallback<CheckList>() {
            @Override
            public void done(List<CheckList> objects, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting activeList", e);
                    return;
                }
                for(CheckList t : objects) {
                    Log.i(TAG, "List Name = " + t.getName());
                    tvChecklistTitle.setText(t.getName());

                }
//                // Remember to CLEAR OUT old items before appending in the new ones
//                checklistAdapter.clear();
//                // ...the data has come back, add new items to your adapter...
//                checklistAdapter.addAll(objects);

                //This should always return 1
                Log.i(TAG, "Num of Lists: " + objects.size());


                queryTasks(objects);
                //checklistAdapter.notifyDataSetChanged();
            }
        });
    }

    private void queryTasks(List<CheckList> checklists){
        // specify what type of data we want to query - Task.class
        ParseQuery<Task> query = ParseQuery.getQuery(Task.class);
        // include data referred by user key
        query.include(Task.KEY_CHECKLIST);
        query.whereContainedIn(Task.KEY_CHECKLIST, checklists);
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Task>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void done(List<Task> tasks, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                // for debugging purposes let's print every post description to logcat

                for(Task t : tasks)
                    Log.i(TAG,"Task Name = "+t.getName());

                // Remember to CLEAR OUT old items before appending in the new ones
                adapter.clear();
                // ...the data has come back, add new items to your adapter...
                adapter.addAll(tasks);

                Log.i(TAG, "Num of Posts: " + tasks.size());
                adapter.notifyDataSetChanged();
            }
        });
    }

}