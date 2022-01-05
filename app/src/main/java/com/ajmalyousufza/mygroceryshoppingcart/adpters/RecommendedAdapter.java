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
import com.ajmalyousufza.mygroceryshoppingcart.models.RecommendedModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {

    Context context;
    List<RecommendedModel> recommendedModelList;

    public RecommendedAdapter(Context context, List<RecommendedModel> recommendedModelList) {
        this.context = context;
        this.recommendedModelList = recommendedModelList;
    }

    @NonNull
    @Override
    public RecommendedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(recommendedModelList.get(position).getImage_url()).into(holder.recom_img);
        holder.name.setText(recommendedModelList.get(position).getName());
        holder.description.setText(recommendedModelList.get(position).getDescription());
        holder.rating.setText(recommendedModelList.get(position).getRating());
        //holder.name.setText(recommendedModelList.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return recommendedModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView recom_img;
        TextView name,description,rating;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recom_img = itemView.findViewById(R.id.rec_img);
            name = itemView.findViewById(R.id.rec_name);
            description = itemView.findViewById(R.id.rec_description);
            rating = itemView.findViewById(R.id.rec_rating);
        }
    }
}
