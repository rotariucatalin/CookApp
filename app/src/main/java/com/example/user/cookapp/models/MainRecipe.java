package com.example.user.cookapp.models;

/**
 * Created by user on 05.03.2018.
 */

public class MainRecipe {

    private String title    = "";
    private int time       = 0;

    public MainRecipe(String title, int time) {

        this.title      = title;
        this.time      = time;
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
}
