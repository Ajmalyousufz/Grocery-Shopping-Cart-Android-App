package com.ajmalyousufza.mygroceryshoppingcart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ajmalyousufza.mygroceryshoppingcart.R;
import com.ajmalyousufza.mygroceryshoppingcart.adpters.NavCategoryDetailedAdapter;
import com.ajmalyousufza.mygroceryshoppingcart.models.NavCategoryDetailedModel;
import com.ajmalyousufza.mygroceryshoppingcart.models.PopularModel;
import com.ajmalyousufza.mygroceryshoppingcart.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NavCategoryActivity extends AppCompatActivity {

    RecyclerView cat_detailed_recyclerview;
    List<NavCategoryDetailedModel> navCategoryDetailedModelList;
    NavCategoryDetailedAdapter navCategoryDetailedAdapter;
    FirebaseFirestore db;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_category);

        db = FirebaseFirestore.getInstance();
        String type = getIntent().getStringExtra("type");

        progressBar = findViewById(R.id.prgressbar);
        progressBar.setVisibility(View.VISIBLE);

        cat_detailed_recyclerview = findViewById(R.id.nav_category_recyclerview);
        cat_detailed_recyclerview.setVisibility(View.GONE);
        cat_detailed_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        navCategoryDetailedModelList = new ArrayList<>();
        navCategoryDetailedAdapter = new NavCategoryDetailedAdapter(this,navCategoryDetailedModelList);
        cat_detailed_recyclerview.setAdapter(navCategoryDetailedAdapter);

        ////////Getting eggs type from fstore document//////////////

        if(type != null && type.equalsIgnoreCase("drink")){
            db.collection("NavCategoryDetailes").whereEqualTo("type","drink").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                NavCategoryDetailedModel navCategoryDetailedModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                                navCategoryDetailedModelList.add(navCategoryDetailedModel);
                                navCategoryDetailedAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                cat_detailed_recyclerview.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }
    }
}