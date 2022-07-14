package com.example.progress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.progress.Models.Photo;
import com.parse.ParseFile;

public class PhotoDetailActivity extends AppCompatActivity {

    public static final String TAG = "Photo Detailed Activity";
    private TextView tvPhotoDescription;
    private ImageView ivPhoto;
    private Photo photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        tvPhotoDescription = findViewById(R.id.tvDescription);
        ivPhoto = findViewById(R.id.ivDetailPhoto);

        photo = (Photo) getIntent().getParcelableExtra("photo");

        bind(photo);
    }

    public void bind(Photo photo){
        tvPhotoDescription.setText(photo.getKeyDescription());
        ParseFile image = photo.getKeyImage();
        if(image != null)
            Glide.with(this)
                    .load(image.getUrl())
                    .override(ivPhoto.getWidth(), ivPhoto.getHeight())
                    .fitCenter() // scale to fit entire image within ImageView
                    .into(ivPhoto);
        Log.d(TAG, "Bind ran\n Description: "+photo.getKeyDescription());
    }
}