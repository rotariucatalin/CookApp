package com.example.user.cookapp.models;

import android.graphics.Bitmap;

public class SelectedRecipeList {

    private int id              = 0;
    private String title        = "";
    private int time            = 0;
    private int portion         = 0;
    private String level        = "";
    private String linkImage    = "";

    public SelectedRecipeList(int id, String title, int time, int portion, String level, String linkImage) {

        this.id             = id;
        this.title          = title;
        this.time           = time;
        this.portion        = portion;
        this.level          = level;
        this.linkImage      = linkImage;
    }

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public int getTime() { return time; }
    public void setPortion(int portion) { this.portion = portion; }
    public int getPortion() { return portion; }
    public void setLevel(String level) { this.level = level; }
    public String getLevel() { return level; }
    public void setLinkImage(String linkImage) { this.linkImage = linkImage; }
    public String getLinkImage() { return linkImage; }
}
