package com.ajmalyousufza.mygroceryshoppingcart.adpters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajmalyousufza.mygroceryshoppingcart.R;
import com.ajmalyousufza.mygroceryshoppingcart.models.MyCartModel;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> myCartModelList;
    int totoalPrice = 0;

    public MyCartAdapter(Context context, List<MyCartModel> myCartModelList) {
        this.context = context;
        this.myCartModelList = myCartModelList;
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {

        holder.name.setText(myCartModelList.get(position).getProductName());
        holder.prize.setText(myCartModelList.get(position).getProductPrice());
        holder.date.setText(myCartModelList.get(position).getProductDate());
        holder.time.setText(myCartModelList.get(position).getProductTime());
        holder.totalPrize.setText(String.valueOf(myCartModelList.get(position).getTotalPrice()));
        holder.totalQuantity.setText(String.valueOf(myCartModelList.get(position).getTotalQuantity()));

        totoalPrice = totoalPrice+myCartModelList.get(position).getTotalPrice();
        Intent intent = new Intent("MytotoalAmount");
        intent.putExtra("totalAmount",totoalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    @Override
    public int getItemCount() {
        return myCartModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,prize,date,time,totalPrize,totalQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.my_cart_item_name);
            prize = itemView.findViewById(R.id.my_cart_item_price);
            date = itemView.findViewById(R.id.my_cart_item_date);
            time = itemView.findViewById(R.id.my_cart_item_time);
            totalPrize = itemView.findViewById(R.id.my_cart_item_total_prize);
            totalQuantity = itemView.findViewById(R.id.my_cart_item_total_quantity);
        }
    }
}
