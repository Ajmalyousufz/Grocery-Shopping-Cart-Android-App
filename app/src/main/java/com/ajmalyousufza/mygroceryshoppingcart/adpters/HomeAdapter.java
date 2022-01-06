package com.ajmalyousufza.mygroceryshoppingcart.adpters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajmalyousufza.mygroceryshoppingcart.R;
import com.ajmalyousufza.mygroceryshoppingcart.activities.ViewAllActivity;
import com.ajmalyousufza.mygroceryshoppingcart.models.HomeCategory;
import com.bumptech.glide.Glide;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context context;
    List<HomeCategory> homeCategories;

    public HomeAdapter(Context context, List<HomeCategory> homeCategories) {
        this.context = context;
        this.homeCategories = homeCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cat_items,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(homeCategories.get(position).getImg_src()).into(holder.catImg);
        holder.name.setText(homeCategories.get(position).getName());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ViewAllActivity.class);
            intent.putExtra("type",homeCategories.get(position).getType());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return homeCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView catImg;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImg = itemView.findViewById(R.id.home_cat_image);
            name = itemView.findViewById(R.id.home_cat_name);
        }
    }
}
