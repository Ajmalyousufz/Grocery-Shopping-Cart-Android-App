package com.ajmalyousufza.mygroceryshoppingcart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.ajmalyousufza.mygroceryshoppingcart.R;
import com.ajmalyousufza.mygroceryshoppingcart.adpters.ViewAllAdapter;
import com.ajmalyousufza.mygroceryshoppingcart.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView view_all_reclerview;
    ViewAllAdapter viewAllAdapter;
    List<ViewAllModel> viewAllModelList;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        progressBar = findViewById(R.id.prgressbar);
        progressBar.setVisibility(View.VISIBLE);

        db = FirebaseFirestore.getInstance();
        String type = getIntent().getStringExtra("type");
        view_all_reclerview = findViewById(R.id.view_all_recyclerview);
        view_all_reclerview.setVisibility(View.GONE);
        view_all_reclerview.setLayoutManager(new LinearLayoutManager(this));

        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(this,viewAllModelList);
        view_all_reclerview.setAdapter(viewAllAdapter);

        ////////Getting Fruits type from fstore document//////////////

        if(type != null && type.equalsIgnoreCase("fruit")){
            db.collection("Add Products").whereEqualTo("type","fruit").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        view_all_reclerview.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        ////////Getting milk type from fstore document//////////////

        if(type != null && type.equalsIgnoreCase("milk")){
            db.collection("Add Products").whereEqualTo("type","milk").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                                viewAllModelList.add(viewAllModel);
                                viewAllAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                view_all_reclerview.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        ////////Getting fish type from fstore document//////////////

        if(type != null && type.equalsIgnoreCase("fish")){
            db.collection("Add Products").whereEqualTo("type","fish").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                                viewAllModelList.add(viewAllModel);
                                viewAllAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                view_all_reclerview.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        ////////Getting eggs type from fstore document//////////////

        if(type != null && type.equalsIgnoreCase("eggs")){
            db.collection("Add Products").whereEqualTo("type","eggs").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                                viewAllModelList.add(viewAllModel);
                                viewAllAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                view_all_reclerview.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        ////////Getting Vegetables type from fstore document//////////////

        if(type != null && type.equalsIgnoreCase("vegetable")){
            db.collection("Add Products").whereEqualTo("type","vegetable").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                                viewAllModelList.add(viewAllModel);
                                viewAllAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                view_all_reclerview.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }
    }
}