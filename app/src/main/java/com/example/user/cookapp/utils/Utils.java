package com.example.user.cookapp.utils;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Base64;

public class Utils extends Application {

    public static final String REQUEST_LINK = "http://192.168.105.160/cook_app/request.php";
    private static Utils singletonUtils = new Utils();

    private Bundle bundle;
    private NavigationView navigationView;

    public Bitmap convertBitmapUrl(String urlString) {

        byte[] decodedString = Base64.decode(urlString, Base64.DEFAULT);
        final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;
    }

    public static Utils getInstance() {
        return singletonUtils;
    }

    public void setBundle(Bundle bundle){
        this.bundle = bundle;
    }

    public void setNavigationView (NavigationView bundleNavigation) { this.navigationView = bundleNavigation; }

    public NavigationView getNavigationView() { return this.navigationView; }

    public Bundle getBundle() {
        return this.bundle;
    }

    public void disableMenuOptionClicked(){

        int menuSize = navigationView.getMenu().size();
        for (int i = 0; i <= menuSize - 1; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singletonUtils = this;
    }
}

