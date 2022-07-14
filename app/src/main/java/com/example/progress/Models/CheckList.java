package com.example.progress.Models;


import com.parse.ParseClassName;
import com.parse.ParseFile;
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
    public static final String hasQuiz = "hasQuiz";
    public static final String hasPhotos = "hasPhotos";
    public static final String KEY_THUMBNAIL = "thumbnail";


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
    public void setDescription(String description) {
        put(KEY_DESCRIPTION,description);
    }

    public ParseObject getUser(){
        return getParseObject(KEY_USER);
    }
    public void setUser(ParseObject user){
        put(KEY_USER,user);
    }

    public Boolean getIsActive() {
        return getBoolean(isActive);
    }
    //TODO: Implement setting active lists
    public void setIsActive(Boolean bool){put(isActive, bool);}

    public Boolean getHasPhotos() {
        return getBoolean(hasPhotos);
    }
    public void setHasPhotos(Boolean bool){put(hasPhotos, bool);}

    public Boolean getHasQuiz() {
        return getBoolean(hasQuiz);
    }
    public void setHasQuiz(Boolean bool){put(hasQuiz, bool);}

    public ParseFile getKeyThumbnail() {return getParseFile(KEY_THUMBNAIL);}
    public void setKeyThumbnail(ParseFile thumbnail){put(KEY_THUMBNAIL, thumbnail);}
}
