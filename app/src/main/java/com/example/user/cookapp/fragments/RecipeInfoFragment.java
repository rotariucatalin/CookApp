package com.example.user.cookapp.fragments;



import android.app.ActionBar;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.cookapp.activities.MainActivity;
import com.example.user.cookapp.async_tasks.HttpRequest;
import com.example.user.cookapp.interfaces.UtilityInterface;
import com.example.user.cookapp.utils.Utils;
import com.example.user.cookingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeInfoFragment extends Fragment {

    public static final String REQUEST_TYPE         = "request_recipe_info";
    public static final String REQUEST_TYPE_FLOAT   = "insert_like_recipe";
    private String recipeTitle                      = "";
    private int recipeLiked                         = 0;
    private int userID                              = 0;
    private int recipeID                            = 0;
    private String recipePhoto                      = "";

    private AppBarLayout appBarLayout;
    private ImageView imageView;
    private FloatingActionButton floatingActionButton;
    private MainActivity mainActivity;
    public static Toast toast = null;


    public RecipeInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_info, container, false);

        Utils.getInstance().disableMenuOptionClicked();

        final CollapsingToolbarLayout collapsingToolbarLayout   = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        appBarLayout                                            = (AppBarLayout) view.findViewById(R.id.app_bar_layout);
        imageView                                               = (ImageView)view.findViewById(R.id.imageView_list);
        final TableLayout tableLayoutRecipe                     = (TableLayout)view.findViewById(R.id.tableLayoutRecipe);
        floatingActionButton                                    = (FloatingActionButton)view.findViewById(R.id.fab);

        userID  = Utils.getInstance().getBundle().getInt("userID");
        imageView.setPadding(10, 10, 10, 10);

        /*
        *   Retreive the bundle with recipe info and store the id for selected recipe
        * */
        Bundle bundle               = this.getArguments();
        recipeID                    = bundle.getInt("recipeID");

        /*
        *   Create request for server to retreive the recipe info for specific ID
        * */
        HttpRequest httpRequest = new HttpRequest(getContext(), new UtilityInterface() {
            @Override
            public void asyncTaskCompleted(JSONObject jsonObject) {

                try {

                    int count                   = 1;
                    int textCount               = 1;
                    recipeTitle                 = jsonObject.getString("title");
                    recipeLiked                 = jsonObject.getInt("liked");
                    recipePhoto                 = jsonObject.getString("recipe_photo");
                    JSONArray arrJson           = jsonObject.getJSONArray("ingredients");

                    Glide.with(getContext()).load(recipePhoto).into(imageView);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0,10,0,0);

                    TableRow tableRow = null;

                    if(recipeLiked == 1) {

                        floatingActionButton.setImageResource(R.drawable.heart_icon_white_full);
                    } else {

                        floatingActionButton.setImageResource(R.drawable.heart_icon_white);
                    }

                    for(int i = 0; i < arrJson.length(); i++) {

                        JSONObject jsonRecipeList   = new JSONObject( arrJson.getString(i));
                        Iterator keys               = jsonRecipeList.keys();

                        /*
                         *   Create dynamically the table row content
                         *   Each tablerow conteins 2 values so the information from database has to be splitted to correspond
                         * */
                        if (count == 1) {

                            tableRow = new TableRow(getContext());
                            tableRow.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            tableRow.setLayoutParams(params);

                        } else if(count > 2) {

                            if ((count%2)!=0 ) {

                                tableRow = new TableRow(getContext());
                                tableRow.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                tableRow.setLayoutParams(params);
                            }
                        }

                        TextView textView =  new TextView(getContext());

                        while(keys.hasNext()) {
                            String currentDynamicKey = (String)keys.next();

                            textView.setText(jsonRecipeList.getString(currentDynamicKey));
                            textView.setId(count);
                            tableRow.addView(textView);
                        }
                        /*
                         *   `To set different color for each information on each row
                         * */
                        if(textCount == 1) {

                            textView.setTextColor(Color.parseColor("#110820"));
                            textCount++;
                        } else {

                            textView.setTextColor(Color.parseColor("#898196"));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                textView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                            }
                            textCount = 1;
                        }

                        if (count == 1) {

                            tableLayoutRecipe.addView(tableRow);

                        } else if(count > 2) {

                            if ((count%2)!=0 ) {

                                tableLayoutRecipe.addView(tableRow);
                            }
                        }

                        count++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        httpRequest.execute(Utils.REQUEST_LINK,REQUEST_TYPE, String.valueOf(recipeID),String.valueOf(userID),"","","","","","");

        /*
        *   Scrolling event when ColapsingToolbar is scrolled. The image dissapear and a text will be setted.
        * */
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isShow      = true;
            int scrollRange     = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (scrollRange + verticalOffset == 0) {

                    collapsingToolbarLayout.setTitle(recipeTitle);
                    isShow = true;

                } else if(isShow) {

                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
        /*
        *   Floating button action
        *   If the recipe was liked then remove from list otherwise added into liked list
        * */
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(toast != null) toast.cancel();

                HttpRequest httpRequestFloatButton =  new HttpRequest(getContext(), new UtilityInterface() {
                    @Override
                    public void asyncTaskCompleted(JSONObject jsonObject) {

                        String code             = "";
                        String message          = "";
                        String action           = "";

                        try {

                            code    = jsonObject.getString("code");
                            message = jsonObject.getString("message");
                            action  = jsonObject.getString("action");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(code.equals("success")) {

                            if(action.equals("like")) {
                                floatingActionButton.setImageResource(R.drawable.heart_icon_white_full);
                                recipeLiked = 1;
                            } else {
                                floatingActionButton.setImageResource(R.drawable.heart_icon_white);
                                recipeLiked = 0;
                            }

                            toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
                            toast.show();


                        } else {

                            toast = toast.makeText(getContext(), message, Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });

                httpRequestFloatButton.execute(Utils.REQUEST_LINK,REQUEST_TYPE_FLOAT, String.valueOf(recipeID),String.valueOf(userID),String.valueOf(recipeLiked),"","","","","");

            }
        });



        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.getItem(0).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

}
