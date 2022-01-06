package com.ajmalyousufza.mygroceryshoppingcart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ajmalyousufza.mygroceryshoppingcart.adpters.MyCartAdapter;
import com.ajmalyousufza.mygroceryshoppingcart.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    TextView overTotalAmount;

    RecyclerView mycart_recyclerview;
    MyCartAdapter myCartAdapter;
    List<MyCartModel> myCartModelList;

    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_cart, container, false);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        mycart_recyclerview = root.findViewById(R.id.mycart_recyclerview);
        mycart_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        overTotalAmount = root.findViewById(R.id.my_cart_total_prize);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver
                (mMessageReceiver,new IntentFilter("MytotoalAmount"));
        myCartModelList = new ArrayList<>();
        myCartAdapter = new MyCartAdapter(getActivity(),myCartModelList);
        mycart_recyclerview.setAdapter(myCartAdapter);

        firestore.collection("Add to Cart").document(auth.getCurrentUser().getUid())
                .collection("Current User").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                MyCartModel cartModel = documentSnapshot.toObject((MyCartModel.class));
                                myCartModelList.add(cartModel);
                                myCartAdapter.notifyDataSetChanged();
                            }
                        }
                        else {
                            Toast.makeText(getActivity(), "Error to receive cart items", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        return root;
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int billAmount = intent.getIntExtra("totalAmount",0);
            overTotalAmount.setText("Total Bill "+billAmount+"$");
        }
    };
}