package com.ajmalyousufza.mygroceryshoppingcart.adpters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajmalyousufza.mygroceryshoppingcart.R;
import com.ajmalyousufza.mygroceryshoppingcart.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> myCartModelList;
    int totoalPrice = 0;

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    public MyCartAdapter(Context context, List<MyCartModel> myCartModelList) {
        this.context = context;
        this.myCartModelList = myCartModelList;

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
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

        holder.deleteCartItem.setOnClickListener(view -> {
            firestore.collection("Current User").document(auth.getCurrentUser().getUid())
                    .collection("Add to Cart")
                    .document(myCartModelList.get(position).getDocumentId())
                    .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        myCartModelList.remove(myCartModelList.get(position));
                        notifyDataSetChanged();
                        Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(context, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

    }

    @Override
    public int getItemCount() {
        return myCartModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,prize,date,time,totalPrize,totalQuantity;
        ImageView deleteCartItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.my_cart_item_name);
            prize = itemView.findViewById(R.id.my_cart_item_price);
            date = itemView.findViewById(R.id.my_cart_item_date);
            time = itemView.findViewById(R.id.my_cart_item_time);
            totalPrize = itemView.findViewById(R.id.my_cart_item_total_prize);
            totalQuantity = itemView.findViewById(R.id.my_cart_item_total_quantity);
            deleteCartItem = itemView.findViewById(R.id.cart_item_delete);
        }
    }
}
