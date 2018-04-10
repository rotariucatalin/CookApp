package com.example.user.cookapp.adapters;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.cookapp.fragments.SaladFragment;
import com.example.user.cookapp.models.MainRecipe;
import com.example.user.cookingapp.R;

import java.util.List;

/**
 * Created by user on 05.03.2018.
 */

public class MainRecipeAdapter extends RecyclerView.Adapter<MainRecipeAdapter.RecipeHolder> {

    List<MainRecipe> mainRecipes;
    private Context context;
    private FragmentTransaction fragmentTransaction;

    public MainRecipeAdapter(List<MainRecipe> mainRecipes) {
        this.mainRecipes            = mainRecipes;

    }

    @NonNull
    @Override
    public MainRecipeAdapter.RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_main, parent, false);

        return new RecipeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecipeAdapter.RecipeHolder holder, int position) {

        MainRecipe mainRecipe = mainRecipes.get(position);
        holder.titleTextView.setText(mainRecipe.getTitle());
        holder.foodImageBackground.setImageBitmap(mainRecipe.getBackground());

    }

    @Override
    public int getItemCount() {
        return mainRecipes.size();
    }


    public class RecipeHolder extends RecyclerView.ViewHolder {

        public ImageView foodImageBackground;
        public TextView titleTextView;

        public RecipeHolder(View itemView) {
            super(itemView);

            titleTextView           = (TextView)itemView.findViewById(R.id.title);
            foodImageBackground     = (ImageView) itemView.findViewById(R.id.foodImageBackground);

            foodImageBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    int position = getAdapterPosition();
                    android.support.v4.app.Fragment fragment = null;

                    switch (position) {
                        case 0: fragment = new SaladFragment();  break;
                        case 1: fragment = new SaladFragment(); break;
                        case 2: fragment = new SaladFragment(); break;
                        case 3: fragment = new SaladFragment(); break;
                        case 4: fragment = new SaladFragment(); break;
                    }

                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).addToBackStack("").commit();
                }
            });
        }

    }
}
