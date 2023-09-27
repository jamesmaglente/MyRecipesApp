package com.example.highlysucceedexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.highlysucceedexam.Adapters.RVRandomRecipeAdapter;
import com.example.highlysucceedexam.Listeners.RandomRecipeResponseListener;
import com.example.highlysucceedexam.Listeners.RecipeClickListener;
import com.example.highlysucceedexam.Models.RandomRecipeApiResponse;

public class MainActivity extends AppCompatActivity {

    LinearLayout llNetworkError;
    ProgressDialog dialog;
    RequestManager reqManager;
    RVRandomRecipeAdapter rvRandomRecipeAdapter;
    RecyclerView rvRecipes;

    Button btnRetry;

    AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //changing status bar color
        changeStatusBarColor();

        llNetworkError = findViewById(R.id.ll_network_error);
        btnRetry = findViewById(R.id.btn_retry);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

        if(!connected){
            llNetworkError.setVisibility(View.VISIBLE);
        }else {
            loadRecipes();
        }

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

                if(connected) {
                    llNetworkError.setVisibility(View.GONE);
                    loadRecipes();
                }else{
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void loadRecipes() {
        View viewInflated = null;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this, com.airbnb.lottie.R.style.Theme_AppCompat_Dialog_Alert);
        alertDialogBuilder.setCancelable(false);

        viewInflated = LayoutInflater.from(MainActivity.this).inflate(R.layout.loading_dialog, null);
        alertDialogBuilder.setView(viewInflated);

        loadingDialog = alertDialogBuilder.create();

        if(loadingDialog.getWindow() != null){
            loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        reqManager = new RequestManager(MainActivity.this);
        reqManager.getRandomRecipes(randomRecipeResponseListener);
        loadingDialog.show();
    }

    private  final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            rvRecipes = findViewById(R.id.rv_random_recipes);
            rvRecipes.setHasFixedSize(true);
            rvRecipes.setLayoutManager(new GridLayoutManager(MainActivity.this,1));
            rvRandomRecipeAdapter = new RVRandomRecipeAdapter(MainActivity.this,response.recipes, recipeClickListener);
            rvRecipes.setAdapter(rvRandomRecipeAdapter);
            loadingDialog.dismiss();

        }

        @Override
        public void didError(String message) {
            Toast.makeText(MainActivity.this,message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id,String likes) {
           startActivity(new Intent(MainActivity.this, RecipeDetailsActivity.class)
                   .putExtra("id",id)
                   .putExtra("likes",likes)
           );
        }


    };
}