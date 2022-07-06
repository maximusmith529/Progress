package com.example.progress.Fragments;

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

import com.example.progress.Adapters.AlbumAdapter;
import com.example.progress.Models.CheckList;
import com.example.progress.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReflectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReflectionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String TAG = "Reflection Fragment";
    private RecyclerView rvAlbums;
    protected AlbumAdapter adapter;
    protected List<CheckList> checklists;


    public ReflectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReflectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReflectionFragment newInstance(String param1, String param2) {
        ReflectionFragment fragment = new ReflectionFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reflection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvAlbums = view.findViewById(R.id.rvAlbums);

        checklists = new ArrayList<>();
        adapter = new AlbumAdapter(getContext(), checklists);

        rvAlbums.setAdapter(adapter);
        rvAlbums.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        queryListsForCurrentUser();
    }

    private void queryListsForCurrentUser() {
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
}