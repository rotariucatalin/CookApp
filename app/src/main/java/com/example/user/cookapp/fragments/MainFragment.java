package com.example.user.cookapp.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.cookapp.adapters.MainRecipeAdapter;
import com.example.user.cookapp.models.MainRecipe;
import com.example.user.cookingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MainFragment extends Fragment {

    private MainRecipeAdapter mainRecipeAdapter;
    private List<MainRecipe> mainRecipes;
    private RecyclerView recyclerView;
    private MainRecipe mainRecipe;
    private Context context;

    @SuppressLint("ValidFragment")
    public MainFragment(Context context) {
        // Required empty public constructor
        this.context                = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Bitmap fishfoodbackground       = BitmapFactory.decodeResource(getResources(),R.drawable.fishfoodbackground);
        Bitmap meatfoodbackground       = BitmapFactory.decodeResource(getResources(),R.drawable.meatfoodbackground);
        Bitmap saladfoodbackground      = BitmapFactory.decodeResource(getResources(),R.drawable.saladfoodbackground);
        Bitmap soupfoodbackground       = BitmapFactory.decodeResource(getResources(),R.drawable.soupbackgroundimage);
        Bitmap dessertfoodbackground    = BitmapFactory.decodeResource(getResources(),R.drawable.dessertbackgroundimage);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);

        mainRecipes         = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        mainRecipeAdapter   = new MainRecipeAdapter(mainRecipes);
        mainRecipe          = new MainRecipe("SALATE", saladfoodbackground);
        mainRecipes.add(mainRecipe);
        mainRecipe          = new MainRecipe("PESTE", fishfoodbackground);
        mainRecipes.add(mainRecipe);
        mainRecipe          = new MainRecipe("CARNE", meatfoodbackground);
        mainRecipes.add(mainRecipe);
        mainRecipe          = new MainRecipe("SUPE", soupfoodbackground);
        mainRecipes.add(mainRecipe);
        mainRecipe          = new MainRecipe("DESERT", dessertfoodbackground);
        mainRecipes.add(mainRecipe);

        recyclerView.setAdapter(mainRecipeAdapter);

        return view;
    }

}
