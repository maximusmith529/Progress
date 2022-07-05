package com.example.progress.Models;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.Date;

@ParseClassName("Photo")
public class Photo extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String DATE = "createdAt";
    public static final String KEY_CHECKLIST = "checklistTag";


    public Photo() {
    }

    public String getKeyDescription(){return getString(KEY_DESCRIPTION);}
    public void setKeyDescription(String description){put(KEY_DESCRIPTION, description);}

    public ParseObject getKeyChecklist(){return getParseObject(KEY_CHECKLIST);}
    public void setKeyChecklist(ParseObject checklist){put(KEY_CHECKLIST,checklist);}

    public ParseFile getKeyImage(){return getParseFile(KEY_IMAGE);}
    public void setKeyImage(ParseFile image){put(KEY_IMAGE,image);}

    @Override
    public Date getCreatedAt() {
        return super.getCreatedAt();
    }
}
