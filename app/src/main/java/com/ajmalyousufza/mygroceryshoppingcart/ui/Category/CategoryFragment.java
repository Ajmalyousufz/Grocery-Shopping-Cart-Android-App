package com.ajmalyousufza.mygroceryshoppingcart.ui.Category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajmalyousufza.mygroceryshoppingcart.R;
import com.ajmalyousufza.mygroceryshoppingcart.adpters.NavCategoryAdapter;
import com.ajmalyousufza.mygroceryshoppingcart.adpters.PopularAdatper;
import com.ajmalyousufza.mygroceryshoppingcart.models.NavCategoryModel;
import com.ajmalyousufza.mygroceryshoppingcart.models.PopularModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    List<NavCategoryModel> navCategoryModelList;
    RecyclerView recyclerView;
    NavCategoryAdapter navCategoryAdapter;
    FirebaseFirestore db;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


      View root = inflater.inflate(R.layout.fragment_category,container,false);

      db = FirebaseFirestore.getInstance();

        progressBar = root.findViewById(R.id.prgressbar);
        progressBar.setVisibility(View.VISIBLE);
      recyclerView = root.findViewById(R.id.category_recycler_id);
      recyclerView.setVisibility(View.GONE);

        //Nav Category items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        navCategoryModelList = new ArrayList<>();
        navCategoryAdapter = new NavCategoryAdapter(getActivity(),navCategoryModelList);
        recyclerView.setAdapter(navCategoryAdapter);

        db.collection("NavCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NavCategoryModel navCategoryModel = document.toObject(NavCategoryModel.class);
                                navCategoryModelList.add(navCategoryModel);
                                navCategoryAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                //Toast.makeText(getActivity(), "Successfull.", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(getActivity(), "Error getting documents."+task.getException(), Toast.LENGTH_SHORT).show();
                        }}});

        return root;
    }

}