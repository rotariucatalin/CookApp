package com.example.user.cookapp.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.user.cookapp.fragments.MainFragment;
import com.example.user.cookapp.fragments.MyAccountFragment;
import com.example.user.cookapp.utils.Utils;
import com.example.user.cookingapp.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private Activity activity;
    private FragmentTransaction fragmentTransaction;
    private Bundle longBundle;
    private Utils utils;
    private TextView textViewUser;
    private MainFragment mainFragment = new MainFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = getApplicationContext();
        activity = this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView   = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView textViewUser = (TextView)navigationView.getHeaderView(0).findViewById(R.id.textViewUser);

        longBundle = getIntent().getExtras();

        utils.getInstance().setBundle(longBundle);
        utils.getInstance().setNavigationView(navigationView);

        textViewUser.setText(longBundle.getString("user_first_name").concat(" ").concat(longBundle.getString("user_last_name")));
        SharedPreferences.Editor editor = getSharedPreferences("loginCredentials", MODE_PRIVATE).edit();

        if(longBundle.getBoolean("remember_me") == true) {

            editor.putString("üser_first_name",     longBundle.getString("user_first_name"));
            editor.putString("user_last_name",      longBundle.getString("user_last_name"));
            editor.putString("user_email",          longBundle.getString("user_email"));
            editor.putString("user_password",       longBundle.getString("user_password"));
            editor.putBoolean("user_remember_me",   longBundle.getBoolean("remember_me"));

        } else {

            editor.clear();

        }

        editor.commit();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frag_content_frame, mainFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mainFragment.isVisible()) {
            //If the main fragment is visibile disable the back button
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_account) {
            // Handle the camera action

            MyAccountFragment myAccountFragment = new MyAccountFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frag_content_frame, myAccountFragment);
            fragmentTransaction.addToBackStack("").commit();

        } else if (id == R.id.nav_my_recipe_list) {

        } else if (id == R.id.nav_logout) {

            for(android.support.v4.app.Fragment fragment:getSupportFragmentManager().getFragments()) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }

            Intent redirectToLogin = new Intent(this, LoginActivity.class);
            redirectToLogin.putExtras(longBundle);
            startActivity(redirectToLogin);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
