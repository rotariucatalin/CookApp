package com.example.user.cookapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.cookapp.adapters.SelectedRecipeAdapter;
import com.example.user.cookapp.async_tasks.HttpRequest;
import com.example.user.cookapp.interfaces.UtilityInterface;
import com.example.user.cookapp.models.SelectedRecipeList;
import com.example.user.cookapp.utils.Utils;
import com.example.user.cookingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SaladFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<SelectedRecipeList> saladeSelectedRecipeLists;
    private SelectedRecipeAdapter saladeSelectedRecipeAdapter;
    private SelectedRecipeList saladeSelectedRecipeList;
    private Bundle loginBundle;
    private Context context;

    public static final String REQUEST_TYPE         = "recipe_list";

    public SaladFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = this.getContext();
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_salad, container, false);

        Utils.getInstance().disableMenuOptionClicked();

        loginBundle                                     = getArguments();
        recyclerView                                    = (RecyclerView)view.findViewById(R.id.saladRecycleView);
        saladeSelectedRecipeLists                       = new ArrayList<>();
        RecyclerView.LayoutManager linearLayoutManager  = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        saladeSelectedRecipeAdapter = new SelectedRecipeAdapter(saladeSelectedRecipeLists, context);

        HttpRequest httpRequest = new HttpRequest(getContext(), new UtilityInterface() {

            @Override
            public void asyncTaskCompleted(JSONObject jsonObject) {

                try {
                    JSONArray jsonArray         = jsonObject.getJSONArray("recipes");

                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonRecipeList = new JSONObject(jsonArray.getString(i));

                        String recipeTitle      = "";
                        String recipeTime       = "";
                        String recipeLevel      = "";
                        String recipePortions   = "";
                        String recipeImage      = "";
                        String recipeID         = "";

                        recipeID        = jsonRecipeList.getString("id_recipe");
                        recipeTitle     = jsonRecipeList.getString("recipe_title");
                        recipeTime      = jsonRecipeList.getString("recipe_time");
                        recipePortions  = jsonRecipeList.getString("recipe_portion");
                        recipeLevel     = jsonRecipeList.getString("recipe_level");
                        recipeImage     = jsonRecipeList.getString("recipe_photo");

                        saladeSelectedRecipeList   = new SelectedRecipeList(Integer.parseInt(recipeID),recipeTitle, Integer.parseInt(recipeTime), Integer.parseInt(recipePortions), recipeLevel, recipeImage);
                        saladeSelectedRecipeLists.add(saladeSelectedRecipeList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                recyclerView.setAdapter(saladeSelectedRecipeAdapter);
            }
        });

        httpRequest.execute(Utils.REQUEST_LINK,REQUEST_TYPE,"1","","","","","","","");


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                saladeSelectedRecipeAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onOptionsItemSelected(item);
    }

}
