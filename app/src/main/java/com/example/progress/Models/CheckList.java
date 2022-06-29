package com.example.progress.Models;


import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;

@ParseClassName(("Checklist"))
public class CheckList extends ParseObject {
    private ArrayList<Task> tasks = new ArrayList<Task>();
    public static final String TAG = "Checklist";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_USER = "userID";
    public static final String DATE = "createdAt";
    public static final String isActive = "isActive";

    private Date resetTime; //temporary, need to look into resets

    public String getName() {
        return getString(KEY_NAME);
    }

    public void setName(String name) {
        put(KEY_NAME,name);
    }

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public ParseObject getUser(){
        return getParseObject(KEY_USER);
    }
    public void setUser(ParseObject user){
        put(KEY_USER,user);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION,description);
    }
}
