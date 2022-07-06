package com.example.progress.Fragments;

import android.content.Intent;
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
import android.widget.ImageButton;

import com.example.progress.Adapters.ChecklistAdapter;
import com.example.progress.CreateChecklistActivity;
import com.example.progress.MainActivity;
import com.example.progress.Models.CheckList;
import com.example.progress.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class CheckListsFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = "CheckList Fragment";
    protected ChecklistAdapter adapter;
    protected List<CheckList> checklists;
    private RecyclerView rvChecklists;
    private ImageButton btnToCreateChecklist;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckListsFragment() {
        // Required empty public constructor
    }



    public static CheckListsFragment newInstance(String param1, String param2) {
        CheckListsFragment fragment = new CheckListsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvChecklists = view.findViewById(R.id.rvChecklists);
        btnToCreateChecklist = view.findViewById(R.id.btnToCreateChecklist);
        checklists = new ArrayList<>();
        adapter = new ChecklistAdapter(getContext(), checklists);


        //setAdapter into recycler view
        rvChecklists.setAdapter(adapter);
        rvChecklists.setLayoutManager(new LinearLayoutManager(getContext()));
        btnToCreateChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CreateChecklistActivity.class);
                startActivity(i);
                return;
            }
        });


        queryListsForCurrentUser();
    }

    private void queryListsForCurrentUser(){
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



                }
//                // Remember to CLEAR OUT old items before appending in the new ones
//                checklistAdapter.clear();
//                // ...the data has come back, add new items to your adapter...
//                checklistAdapter.addAll(objects);


                // Remember to CLEAR OUT old items before appending in the new ones
                adapter.clear();
                // ...the data has come back, add new items to your adapter...
                adapter.addAll(objects);

                Log.i(TAG, "Num of Lists: " + checklists.size()+"\n");
                adapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_lists, container, false);
    }
}