package com.example.progress;

import android.app.Application;


import com.example.progress.Models.Task;
import com.parse.Parse.Configuration;
import com.example.progress.Models.CheckList;
import com.parse.Parse;
import com.parse.ParseObject;


public class ParseApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(CheckList.class);
        ParseObject.registerSubclass(Task.class);
        Parse.initialize(new Configuration.Builder(this)
                .applicationId("q0Ri0Hz07JPSEUMz7D8jZUonGknWhBXI4eC4fAz3")
                .clientKey("YnGjWpo9EkiDW1hsp1G61sVkuRUpBSv6h5KHSqLy")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }

}
