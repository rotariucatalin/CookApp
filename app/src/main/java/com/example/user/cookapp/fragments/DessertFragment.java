package com.example.user.cookapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.cookapp.adapters.SelectedRecipeAdapter;
import com.example.user.cookapp.models.SelectedRecipeList;
import com.example.user.cookingapp.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DessertFragment extends Fragment {

    private List<SelectedRecipeList> dessertSelectedRecipeListLists;
    private SelectedRecipeAdapter selectedRecipeAdapter;
    private SelectedRecipeList dessertSelectedRecipeList;

    public DessertFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dessert, container, false);



        return view;
    }

}
