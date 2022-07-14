package com.example.progress.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Task")
public class Task extends ParseObject {
    public static final String TAG = "Task";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_CHECKLIST = "parentList";
    public static final String KEY_FINISHED = "finished";
    public static final String DATE = "createdAt";

    public Task() {
    }

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

    public ParseObject getChecklist(){
        return getParseObject(KEY_CHECKLIST);
    }
    public void setChecklist(ParseObject checklist){
        put(KEY_CHECKLIST,checklist);
    }

    public boolean isFinished() {
        return getBoolean(KEY_FINISHED);
    }

    public void setFinished(boolean finished) {
        put(KEY_FINISHED,finished);
    }
}
