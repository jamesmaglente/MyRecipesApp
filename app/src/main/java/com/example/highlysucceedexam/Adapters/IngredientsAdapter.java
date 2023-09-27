package com.example.highlysucceedexam.Adapters;

import static androidx.core.app.NotificationCompat.getColor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.highlysucceedexam.Models.ExtendedIngredient;
import com.example.highlysucceedexam.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {

    Context context;
    List<ExtendedIngredient> list;

    public IngredientsAdapter(Context context, List<ExtendedIngredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_recipe_ingredients,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {


        String newIngredientName = list.get(position).name.substring(0, 1).toUpperCase() + list.get(position).name.substring(1).toLowerCase();
        holder.tvIngredientName.setText(newIngredientName);
        holder.tvIngredientAmount.setText(list.get(position).original);
        Glide.with(context).load("https://spoonacular.com/cdn/ingredients_100x100/"+list.get(position).image).into(holder.ivIngredientImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class IngredientsViewHolder extends RecyclerView.ViewHolder {

    TextView tvIngredientName, tvIngredientAmount;
    ImageView ivIngredientImage;
    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);

        tvIngredientName = itemView.findViewById(R.id.tv_ingredient_name);
        tvIngredientAmount = itemView.findViewById(R.id.tv_ingredient_amount);
        ivIngredientImage = itemView.findViewById(R.id.iv_ingredient_image);

    }
}
