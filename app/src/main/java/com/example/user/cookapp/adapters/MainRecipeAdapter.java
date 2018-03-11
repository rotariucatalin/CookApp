package com.example.user.cookapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.cookingapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import com.example.user.cookapp.models.MainRecipe;

/**
 * Created by user on 05.03.2018.
 */

public class MainRecipeAdapter extends RecyclerView.Adapter<MainRecipeAdapter.RecipeHolder> {

    List<MainRecipe> mainRecipes;

    public MainRecipeAdapter(List<MainRecipe> mainRecipes) {
        this.mainRecipes = mainRecipes;
    }

    @NonNull
    @Override
    public MainRecipeAdapter.RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new RecipeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecipeAdapter.RecipeHolder holder, int position) {

        MainRecipe mainRecipe = mainRecipes.get(position);
        holder.titleTextView.setText(mainRecipe.getTitle());
        holder.timeTextView.setText(String.valueOf(mainRecipe.getTime()));

    }

    @Override
    public int getItemCount() {
        return mainRecipes.size();
    }


    public class RecipeHolder extends RecyclerView.ViewHolder{

        public CircleImageView thumImageView;
        public TextView titleTextView, timeTextView;

        public RecipeHolder(View itemView) {
            super(itemView);

            thumImageView           = (CircleImageView)itemView.findViewById(R.id.thumbnail);
            titleTextView           = (TextView)itemView.findViewById(R.id.title);
            timeTextView            = (TextView)itemView.findViewById(R.id.time);
        }

    }
}
