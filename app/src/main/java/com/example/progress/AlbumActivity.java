package com.example.progress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.progress.Adapters.PhotoAdapter;
import com.example.progress.Models.CheckList;
import com.example.progress.Models.Photo;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    private CheckList checklist;
    private List<Photo> photos;
    private RecyclerView rvPhotos;
    private PhotoAdapter adapter;
    public static final String TAG = "Album Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        photos = new ArrayList<>();
        rvPhotos = findViewById(R.id.rvPhotos);
        checklist = getIntent().getParcelableExtra("checklist");
        adapter = new PhotoAdapter(this, photos);
        rvPhotos.setAdapter(adapter);
        rvPhotos.setLayoutManager(new LinearLayoutManager(this));

        int numCols = 3;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numCols);
        rvPhotos.setLayoutManager(gridLayoutManager);

        queryPhotos();
    }

    private void queryPhotos() {

        // get only checklists
        ParseQuery<Photo> query = ParseQuery.getQuery(Photo.class);
        // only from user
        query.include(Photo.KEY_CHECKLIST);
        query.include(Photo.KEY_IMAGE);
        query.whereEqualTo(Photo.KEY_CHECKLIST, checklist);
        query.orderByDescending("created_at");
        query.findInBackground(new FindCallback<Photo>() {
            @Override
            public void done(List<Photo> objects, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting lists", e);
                    return;
                }
                for(Photo t : objects) {
                    Log.i(TAG, "Photo name = " + t.getKeyDescription());
                }


                // Remember to CLEAR OUT old items before appending in the new ones
                adapter.clear();
                // ...the data has come back, add new items to your adapter...
                adapter.addAll(objects);

                Log.i(TAG, "Num of Photos: " + photos.size()+"\n");
                adapter.notifyDataSetChanged();
            }
        });

    }

}