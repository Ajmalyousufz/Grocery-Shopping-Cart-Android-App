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
import com.ajmalyousufza.mygroceryshoppingcart.models.ViewAllModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {

    Context context;
    List<ViewAllModel> viewAllModelList;

    public ViewAllAdapter(Context context, List<ViewAllModel> viewAllModelList) {
        this.context = context;
        this.viewAllModelList = viewAllModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(viewAllModelList.get(position).getImage_url()).into(holder.viewall_image);
        holder.name.setText(viewAllModelList.get(position).getName());
        holder.description.setText(viewAllModelList.get(position).getDescription());
        holder.rating.setText(viewAllModelList.get(position).getRating());
        int price_int = viewAllModelList.get(position).getPrice();
        String price_str = Integer.toString(price_int);
        holder.price.setText(price_str);

    }

    @Override
    public int getItemCount() {
        return viewAllModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView viewall_image;
        TextView name,description,rating,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewall_image = itemView.findViewById(R.id.view_all_item_image);
            name = itemView.findViewById(R.id.view_all_item_name);
            description = itemView.findViewById(R.id.view_all_item_description);
            rating = itemView.findViewById(R.id.view_all_rating);
            price = itemView.findViewById(R.id.view_all_price_value);
        }
    }
}
