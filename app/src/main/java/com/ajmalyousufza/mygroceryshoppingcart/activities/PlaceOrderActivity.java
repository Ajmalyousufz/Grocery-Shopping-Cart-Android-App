package com.ajmalyousufza.mygroceryshoppingcart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.ajmalyousufza.mygroceryshoppingcart.R;
import com.ajmalyousufza.mygroceryshoppingcart.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceOrderActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        List<MyCartModel> myCartModelList = (ArrayList<MyCartModel>) getIntent().getSerializableExtra("itemlist");

        if(myCartModelList != null && myCartModelList.size()>0){
            for(MyCartModel myCartModel : myCartModelList){

                final HashMap<String,Object> cartMap = new HashMap<>();

                cartMap.put("productName",myCartModel.getProductName());
                cartMap.put("productPrice",myCartModel.getProductPrice());
                cartMap.put("productDate",myCartModel.getProductDate());
                cartMap.put("productTime",myCartModel.getProductTime());
                cartMap.put("totalQuantity",myCartModel.getTotalQuantity());
                cartMap.put("totalPrice",myCartModel.getTotalPrice());

                firestore.collection("Current User").document(auth.getCurrentUser().getUid())
                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(PlaceOrderActivity.this, "Your Order Has been Placed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}