package com.example.user.cookapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.cookapp.models.SelectedRecipeList;
import com.example.user.cookingapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectedRecipeAdapter extends RecyclerView.Adapter<SelectedRecipeAdapter.SelectedRecipeHolder> {

    List<SelectedRecipeList> selectedRecipeListList;

    @NonNull
    @Override
    public SelectedRecipeAdapter.SelectedRecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_selected, parent, false);

        return new SelectedRecipeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedRecipeAdapter.SelectedRecipeHolder holder, int position) {

        SelectedRecipeList selectedRecipeList = selectedRecipeListList.get(position);
        holder.titleTextView.setText(selectedRecipeList.getTitle());
        holder.levelTextView.setText(selectedRecipeList.getLevel());
        holder.portionTextView.setText(selectedRecipeList.getPortion());
    }

    @Override
    public int getItemCount() {
        return selectedRecipeListList.size();
    }

    public class SelectedRecipeHolder extends RecyclerView.ViewHolder {

        private CircleImageView thumbnailImageView;
        private TextView titleTextView, portionTextView, timeTextView, levelTextView;

        public SelectedRecipeHolder(View itemView) {
            super(itemView);

            thumbnailImageView  = (CircleImageView)itemView.findViewById(R.id.thumbnail);
            titleTextView       = (TextView)itemView.findViewById(R.id.title);
            portionTextView     = (TextView)itemView.findViewById(R.id.portion);
            timeTextView        = (TextView)itemView.findViewById(R.id.time);
            levelTextView       = (TextView)itemView.findViewById(R.id.level);
        }
    }
}
