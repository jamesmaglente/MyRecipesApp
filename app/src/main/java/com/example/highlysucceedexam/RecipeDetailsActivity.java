package com.example.highlysucceedexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.highlysucceedexam.Adapters.IngredientsAdapter;
import com.example.highlysucceedexam.Listeners.RecipeDetailsListener;
import com.example.highlysucceedexam.Models.RecipeDetailsResponse;

public class RecipeDetailsActivity extends AppCompatActivity {

    int id;
    String likes;
    ImageView ivFood;
    TextView tvRecipeName,tvLikes, tvServings, tvPrice, tvTime, tvDescription, tvIngredientQuantity;
    RecyclerView rvIngredients;
    RequestManager requestManager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;

    AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        likes = getIntent().getStringExtra("likes");

        View viewInflated = null;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RecipeDetailsActivity.this, com.airbnb.lottie.R.style.Theme_AppCompat_Dialog_Alert);
        alertDialogBuilder.setCancelable(false);

        viewInflated = LayoutInflater.from(RecipeDetailsActivity.this).inflate(R.layout.loading_dialog, null);
        alertDialogBuilder.setView(viewInflated);

        loadingDialog = alertDialogBuilder.create();

        if(loadingDialog.getWindow() != null){
            loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        requestManager = new RequestManager(this);
        requestManager.getRecipeDetails(recipeDetailsListener,id);
        loadingDialog.show();

    }

    private void findViews() {
        ivFood = findViewById(R.id.iv_food);
        tvRecipeName = findViewById(R.id.tv_recipe_name);
        tvLikes = findViewById(R.id.tv_likes);
        tvServings = findViewById(R.id.tv_servings);
        tvPrice = findViewById(R.id.tv_price);
        tvTime = findViewById(R.id.tv_time);
        tvDescription = findViewById(R.id.tv_description);
        rvIngredients = findViewById(R.id.rv_ingredients);
        tvIngredientQuantity = findViewById(R.id.no_of_ingredients);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    //Recipe
    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {

            Glide.with(RecipeDetailsActivity.this).load(response.image).into(ivFood);
            tvRecipeName.setText(response.title);
            tvTime.setText(response.readyInMinutes + " Minutes");
            tvServings.setText(response.servings + " Serving");
            tvPrice.setText(response.pricePerServing + " Price");
            tvLikes.setText(likes + " Likes");
            tvDescription.setText(Html.fromHtml(response.summary).toString());
            loadingDialog.dismiss();

            rvIngredients.setHasFixedSize(true);
            rvIngredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this,LinearLayoutManager.VERTICAL,false));
            ingredientsAdapter = new IngredientsAdapter(RecipeDetailsActivity.this,response.extendedIngredients);
            rvIngredients.setAdapter(ingredientsAdapter);
            tvIngredientQuantity.setText("Ingredients("+ingredientsAdapter.getItemCount()+")");
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}