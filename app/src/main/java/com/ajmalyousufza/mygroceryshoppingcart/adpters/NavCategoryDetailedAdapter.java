package com.ajmalyousufza.mygroceryshoppingcart.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajmalyousufza.mygroceryshoppingcart.R;
import com.ajmalyousufza.mygroceryshoppingcart.models.NavCategoryDetailedModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class NavCategoryDetailedAdapter extends RecyclerView.Adapter<NavCategoryDetailedAdapter.ViewHolder> {

    Context context;
    List<NavCategoryDetailedModel> navCategoryDetailedModelList;

    public NavCategoryDetailedAdapter(Context context, List<NavCategoryDetailedModel> navCategoryDetailedModelList) {
        this.context = context;
        this.navCategoryDetailedModelList = navCategoryDetailedModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_category_detailed_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(navCategoryDetailedModelList.get(position).getImage_url()).into(holder.cat_detail_image);
        holder.name.setText(navCategoryDetailedModelList.get(position).getName());
        holder.price.setText(navCategoryDetailedModelList.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return navCategoryDetailedModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cat_detail_image;
        TextView name,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_detail_image = itemView.findViewById(R.id.nav_catogory_detailed_item_image);
            name = itemView.findViewById(R.id.nav_category_detailed_name);
            price = itemView.findViewById(R.id.nav_category_detailed_price);
        }
    }
}
