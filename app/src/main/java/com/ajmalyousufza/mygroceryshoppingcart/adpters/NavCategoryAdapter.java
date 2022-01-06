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
import com.ajmalyousufza.mygroceryshoppingcart.activities.DetailedActivity;
import com.ajmalyousufza.mygroceryshoppingcart.activities.NavCategoryActivity;
import com.ajmalyousufza.mygroceryshoppingcart.activities.ViewAllActivity;
import com.ajmalyousufza.mygroceryshoppingcart.models.NavCategoryModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class NavCategoryAdapter extends RecyclerView.Adapter<NavCategoryAdapter.ViewHolder> {

     Context context;
     List<NavCategoryModel> navCategoryModelList;

    public NavCategoryAdapter(Context context, List<NavCategoryModel> navCategoryModelList) {
        this.context = context;
        this.navCategoryModelList = navCategoryModelList;
    }

    @NonNull
      @Override
      public NavCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_category_item,parent,false));
      }

      @Override
      public void onBindViewHolder(@NonNull NavCategoryAdapter.ViewHolder holder, int position) {

          Glide.with(context).load(navCategoryModelList.get(position).getImage_url()).into(holder.nav_cat_img);
          holder.name.setText(navCategoryModelList.get(position).getName());
          holder.description.setText(navCategoryModelList.get(position).getDescription());
          holder.discount.setText(navCategoryModelList.get(position).getDiscount());

          holder.itemView.setOnClickListener(view -> {
              Intent intent = new Intent(context, NavCategoryActivity.class);
              intent.putExtra("type",navCategoryModelList.get(position).getType());
              context.startActivity(intent);
          });

      }

      @Override
      public int getItemCount() {
          return navCategoryModelList.size();
      }

      public class ViewHolder extends RecyclerView.ViewHolder {
          ImageView nav_cat_img;
          TextView name,description,discount;
          public ViewHolder(@NonNull View itemView) {
              super(itemView);

              nav_cat_img = itemView.findViewById(R.id.nav_catogory_item_image);
              name = itemView.findViewById(R.id.nav_category_name);
              description = itemView.findViewById(R.id.nav_category_description);
              discount = itemView.findViewById(R.id.nav_category_discountrate);
          }
      }
  }
