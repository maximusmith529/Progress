package com.example.progress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.progress.Models.Photo;
import com.parse.ParseFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class PhotoDetailActivity extends AppCompatActivity {

    public static final String TAG = "Photo Detailed Activity";
    private TextView tvPhotoDescription;
    private ImageView ivPhoto;
    private Photo photo;
    private Button btnPostToInstagram;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        tvPhotoDescription = findViewById(R.id.tvDescription);
        ivPhoto = findViewById(R.id.ivDetailPhoto);
        btnPostToInstagram = findViewById(R.id.btnPostToInstagram);
        photo = (Photo) getIntent().getParcelableExtra("photo");

        bind(photo);

        btnPostToInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                if (intent != null)
                {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setPackage("com.instagram.android");
                    try {
                        ParseFile image = photo.getKeyImage();
                        URI url = new URL(photo.getKeyImage().getUrl()).toURI();
//                        Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        shareIntent.putExtra(Intent.EXTRA_STREAM, (photo.getKeyImage().getUrl()));
                    } catch (MalformedURLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    shareIntent.setType("image/jpeg");

                    startActivity(shareIntent);
                }
                else
                {
                    // bring user to the market to download the app.
                    // or let them choose an app?
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    intent.setData(Uri.parse("market://details?id="+"com.instagram.android"));
                    startActivity(intent);
                }
            }
        });
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