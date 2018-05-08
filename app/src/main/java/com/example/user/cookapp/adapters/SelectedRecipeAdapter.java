package com.example.user.cookapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.cookapp.fragments.RecipeInfoFragment;
import com.example.user.cookapp.models.SelectedRecipeList;
import com.example.user.cookingapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectedRecipeAdapter extends RecyclerView.Adapter<SelectedRecipeAdapter.SelectedRecipeHolder> implements Filterable{

    List<SelectedRecipeList> selectedRecipeListList;
    List<SelectedRecipeList> selectedRecipeListListFilter;
    private Bundle loginBundle;
    private Context context;

    public SelectedRecipeAdapter(List<SelectedRecipeList> selectedRecipeListList, Context context) {

        this.selectedRecipeListList         = selectedRecipeListList;
        this.selectedRecipeListListFilter   = selectedRecipeListList;
        this.context                        = context;
    }

    @NonNull
    @Override
    public SelectedRecipeAdapter.SelectedRecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_selected, parent, false);

        return new SelectedRecipeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedRecipeAdapter.SelectedRecipeHolder holder, int position) {

        final SelectedRecipeList selectedRecipeList = selectedRecipeListListFilter.get(position);

        Glide.with(context).load(selectedRecipeList.getLinkImage()).into(holder.thumbnailImageView);
        holder.titleTextView.setText(selectedRecipeList.getTitle());
        holder.levelTextView.setText(selectedRecipeList.getLevel());
        holder.portionTextView.setText(Integer.toString(selectedRecipeList.getPortion()));

        final Bundle bundle = new Bundle();
        bundle.putInt("recipeID", selectedRecipeList.getId());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RecipeInfoFragment recipeInfoFragment = new RecipeInfoFragment();
                recipeInfoFragment.setArguments(bundle);

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frag_content_frame,recipeInfoFragment).addToBackStack("").commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedRecipeListListFilter.size();
    }

    @Override
    public android.widget.Filter getFilter() {

        return new android.widget.Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if(charString.isEmpty()) {

                    selectedRecipeListListFilter = selectedRecipeListList;

                } else {

                    List<SelectedRecipeList> filteredList = new ArrayList<>();

                    for (SelectedRecipeList selectedRecipeList : selectedRecipeListList) {

                        if (selectedRecipeList.getLevel().toLowerCase().contains(charString) || selectedRecipeList.getTitle().toLowerCase().contains(charString) || selectedRecipeList.getLevel().contains(charString)) {

                            filteredList.add(selectedRecipeList);
                        }
                    }

                    selectedRecipeListListFilter = filteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = selectedRecipeListListFilter;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                selectedRecipeListListFilter = (List<SelectedRecipeList>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    public class SelectedRecipeHolder extends RecyclerView.ViewHolder {

        public CircleImageView thumbnailImageView;
        public CardView card_view;
        public TextView titleTextView, portionTextView, timeTextView, levelTextView;

        public SelectedRecipeHolder(View itemView) {
            super(itemView);

            thumbnailImageView  = (CircleImageView)itemView.findViewById(R.id.thumbnail);
            titleTextView       = (TextView)itemView.findViewById(R.id.title);
            portionTextView     = (TextView)itemView.findViewById(R.id.portion);
            timeTextView        = (TextView)itemView.findViewById(R.id.time);
            levelTextView       = (TextView)itemView.findViewById(R.id.level);
            card_view           = (CardView)itemView.findViewById(R.id.card_view);
        }
    }
}
