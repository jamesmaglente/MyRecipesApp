package com.example.highlysucceedexam.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.highlysucceedexam.Listeners.RecipeClickListener;
import com.example.highlysucceedexam.Models.Recipe;
import com.example.highlysucceedexam.R;

import org.w3c.dom.Text;

import java.util.List;

public class RVRandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder> {

    Context context;
    List<Recipe> list;
    RecipeClickListener listener;

    public RVRandomRecipeAdapter(Context context, List<Recipe> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        holder.tvRecipeName.setText(list.get(position).title);
        holder.tvRecipeName.setSelected(true);
        holder.tvLikes.setText(list.get(position).aggregateLikes + "");
        holder.tvPrice.setText((list.get(position).pricePerServing) + "");
        String newDescription = list.get(position).dishTypes.toString().replace("[","");
        holder.tvDescription.setText(newDescription.replace("]",""));
        Glide.with(context).load(list.get(position).image).into(holder.ivFood);
        holder.randomListContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClicked(String.valueOf(list.get(holder.getAdapterPosition()).id),String.valueOf(list.get(holder.getAdapterPosition()).aggregateLikes));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}



class RandomRecipeViewHolder extends RecyclerView.ViewHolder {

    CardView randomListContainer;
    TextView tvRecipeName, tvLikes, tvPrice, tvDescription;
    ImageView ivFood;


    public RandomRecipeViewHolder(@NonNull View itemView){
        super(itemView);
        randomListContainer = itemView.findViewById(R.id.random_recipe_list_container);
        tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
        tvLikes = itemView.findViewById(R.id.tv_likes);
        tvPrice = itemView.findViewById(R.id.tv_price);
        tvDescription = itemView.findViewById(R.id.tv_description);
        ivFood = itemView.findViewById(R.id.iv_food);
    }

}
