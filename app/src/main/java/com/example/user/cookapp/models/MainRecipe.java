package com.example.user.cookapp.models;

import android.graphics.Bitmap;

/**
 * Created by user on 05.03.2018.
 */

public class MainRecipe {

    private String title  = "";
    private Bitmap background;

    public MainRecipe(String title, Bitmap background) {

        this.title      = title;
        this.background = background;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setBackground(Bitmap background) { this.background = background; }
    public Bitmap getBackground() { return background; }
}
