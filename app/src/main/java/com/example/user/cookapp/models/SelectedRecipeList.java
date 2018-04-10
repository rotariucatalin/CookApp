package com.example.user.cookapp.models;

public class SelectedRecipeList {

    private String title    = "";
    private int time        = 0;
    private int portion     = 0;
    private String level    = "";

    public SelectedRecipeList(String title, int time, int portion, String level) {

        this.title      = title;
        this.time       = time;
        this.portion    = portion;
        this.level      = level;
    }

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
}
