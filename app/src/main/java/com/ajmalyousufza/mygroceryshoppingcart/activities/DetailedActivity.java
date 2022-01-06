package com.ajmalyousufza.mygroceryshoppingcart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ajmalyousufza.mygroceryshoppingcart.R;
import com.ajmalyousufza.mygroceryshoppingcart.models.ViewAllModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    TextView quantity;
    int total_Quantity=1;
    int total_price=0;
    ImageView detailed_product_image;
    TextView price,rating,description;
    Button add_to_cart;
    ImageView add_item,remove_item;
    Toolbar toolbar;

    ViewAllModel viewAllModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.toolbar_detiled);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");

        if(object instanceof ViewAllModel){
            viewAllModel = (ViewAllModel)object;
        }

        detailed_product_image = findViewById(R.id.detailed_image);
        price                  = findViewById(R.id.detailed_price);
        rating                 = findViewById(R.id.detailed_rate_value);
        description            = findViewById(R.id.detailed_decription);
        add_item               = findViewById(R.id.detailed_add_item);
        remove_item            = findViewById(R.id.detailed_remove_item);
        add_to_cart            = findViewById(R.id.detailed_add_to_cart_btn);
        quantity             = findViewById(R.id.detailed_item_added_count);

        if(viewAllModel != null){

            Glide.with(getApplicationContext()).load(viewAllModel.getImage_url()).into(detailed_product_image);
            price.setText("Price : $"+viewAllModel.getPrice()+"/kg");
            total_price = viewAllModel.getPrice()*total_Quantity;
            if(viewAllModel.getType().equals("eggs")){
                price.setText("Price : $"+viewAllModel.getPrice()+"/dozen");
                total_price = viewAllModel.getPrice()*total_Quantity;
            }
            if(viewAllModel.getType().equals("milk")){
                price.setText("Price : $"+viewAllModel.getPrice()+"/litr");
                total_price = viewAllModel.getPrice()*total_Quantity;
            }
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
           // item_count.setText(viewAllModel.getPrice());
        }

        add_to_cart.setOnClickListener(view -> {
            addedToCart();
        });

        add_item.setOnClickListener(view -> {
            if(total_Quantity<10){
                total_Quantity++;
                quantity.setText(String.valueOf(total_Quantity));
                total_price = viewAllModel.getPrice()*total_Quantity;
            }
            else {
                Toast.makeText(getApplicationContext(), "One Order includes maximum 10 quantity", Toast.LENGTH_SHORT).show();
            }
        });
        remove_item.setOnClickListener(view -> {
            if(total_Quantity>1){
                total_Quantity--;
                quantity.setText(String.valueOf(total_Quantity));
                total_price = viewAllModel.getPrice()*total_Quantity;
            }
            else {
                Toast.makeText(getApplicationContext(), "Must have Minimum 1 quantity", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addedToCart() {
        String saveCurrentDate,saveCurrentTime;
        Calendar calFordate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calFordate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:MM:ss a");
        saveCurrentTime = currentTime.format(calFordate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("productName",viewAllModel.getName());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("productDate",saveCurrentDate);
        cartMap.put("productTime",saveCurrentTime);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",total_price);

        firestore.collection("Current User").document(auth.getCurrentUser().getUid())
                .collection("Add to Cart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this, "Added to A Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}