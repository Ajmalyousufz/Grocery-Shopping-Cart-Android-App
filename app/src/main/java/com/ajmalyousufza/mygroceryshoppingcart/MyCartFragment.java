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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ajmalyousufza.mygroceryshoppingcart.activities.PlaceOrderActivity;
import com.ajmalyousufza.mygroceryshoppingcart.adpters.MyCartAdapter;
import com.ajmalyousufza.mygroceryshoppingcart.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    ProgressBar progressBar;
    TextView overTotalAmount;
    Button buy_now_btn;

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

        buy_now_btn = root.findViewById(R.id.mycart_buy_btn);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        progressBar = root.findViewById(R.id.prgressbar);
        progressBar.setVisibility(View.VISIBLE);
        mycart_recyclerview = root.findViewById(R.id.mycart_recyclerview);
        mycart_recyclerview.setVisibility(View.GONE);
        mycart_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        overTotalAmount = root.findViewById(R.id.my_cart_total_prize);
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver
//                (mMessageReceiver,new IntentFilter("MytotoalAmount"));
        myCartModelList = new ArrayList<>();
        myCartAdapter = new MyCartAdapter(getActivity(),myCartModelList);
        mycart_recyclerview.setAdapter(myCartAdapter);

        firestore.collection("Current User").document(auth.getCurrentUser().getUid())
                .collection("Add to Cart").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){

                                String documentId = documentSnapshot.getId();

                                MyCartModel cartModel = documentSnapshot.toObject((MyCartModel.class));

                                cartModel.setDocumentId(documentId);

                                myCartModelList.add(cartModel);
                                myCartAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                mycart_recyclerview.setVisibility(View.VISIBLE);
                            }

                            calculateTotalList(myCartModelList);
                        }
                        else {
                            Toast.makeText(getActivity(), "Error to receive cart items", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        buy_now_btn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
            intent.putExtra("itemlist", (Serializable) myCartModelList);
            startActivity(intent);
        });

        return root;
    }

    private void calculateTotalList(List<MyCartModel> myCartModelList) {
        double totolAmount = 0.0;
        for(MyCartModel myCartModel : myCartModelList){
            totolAmount+=myCartModel.getTotalPrice();
        }
        overTotalAmount.setText("Total Amount : "+totolAmount);
    }
//    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            int billAmount = intent.getIntExtra("totalAmount",0);
//            overTotalAmount.setText("Total Bill "+billAmount+"$");
//        }
//    };
}