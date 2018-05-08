package com.example.user.cookapp.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.cookapp.utils.Utils;
import com.example.user.cookingapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private ImageView saladeImageView, fishImageView, meatImageView, soupImageView, desserImageView;
    private TextView saladeTextView, fishTextView, meatTextView, soupTextView, dessertTextView;
    private FragmentTransaction fragmentTransaction;

    public MainFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        /*
        *   Set all menu items checked to false
        * */
        Utils.getInstance().disableMenuOptionClicked();

        saladeImageView     = (ImageView) view.findViewById(R.id.foodImageBackground1);
        fishImageView       = (ImageView) view.findViewById(R.id.foodImageBackground2);
        meatImageView       = (ImageView) view.findViewById(R.id.foodImageBackground3);
        soupImageView       = (ImageView) view.findViewById(R.id.foodImageBackground4);
        desserImageView     = (ImageView) view.findViewById(R.id.foodImageBackground5);

        saladeTextView      = (TextView) view.findViewById(R.id.title1);
        fishTextView        = (TextView) view.findViewById(R.id.title2);
        meatTextView        = (TextView) view.findViewById(R.id.title3);
        soupTextView        = (TextView) view.findViewById(R.id.title4);
        dessertTextView     = (TextView) view.findViewById(R.id.title5);

        saladeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSelectedRecipes(1);
            }
        });

        fishImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSelectedRecipes(2);
            }
        });

        meatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSelectedRecipes(3);
            }
        });

        soupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSelectedRecipes(4);
            }
        });

        desserImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSelectedRecipes(5);
            }
        });
        return view;
    }

    public void goToSelectedRecipes(int position) {

        Fragment fragment = null;

        switch(position) {

            case 1: fragment = new SaladFragment();     break;
            case 2: fragment = new FishFragment();      break;
            case 3: fragment = new MeatFragment();      break;
            case 4: fragment = new SoupFragment();      break;
            case 5: fragment = new DessertFragment();   break;
        }

        fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.frag_content_frame, fragment);
        fragmentTransaction.addToBackStack("").commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.getItem(0).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }


}
