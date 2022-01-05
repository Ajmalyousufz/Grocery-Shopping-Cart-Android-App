package com.ajmalyousufza.mygroceryshoppingcart.ui.home;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajmalyousufza.mygroceryshoppingcart.R;
import com.ajmalyousufza.mygroceryshoppingcart.adpters.HomeAdapter;
import com.ajmalyousufza.mygroceryshoppingcart.adpters.PopularAdatper;
import com.ajmalyousufza.mygroceryshoppingcart.databinding.FragmentHomeBinding;
import com.ajmalyousufza.mygroceryshoppingcart.models.HomeCategory;
import com.ajmalyousufza.mygroceryshoppingcart.models.PopularModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView pop_rec,home_cat_rec;
    FirebaseFirestore db;

    //Popular item
    List<PopularModel> popularModelList;
    PopularAdatper pop_adatper;

    //Home Category
    List<HomeCategory> homeCategoryList;
    HomeAdapter homeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_home,container,false);

       pop_rec = root.findViewById(R.id.pop_rec);
        home_cat_rec = root.findViewById(R.id.explore_rec);

       db = FirebaseFirestore.getInstance();

       //Popular items
        pop_rec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularModelList = new ArrayList<>();
        pop_adatper = new PopularAdatper(getActivity(),popularModelList);
        pop_rec.setAdapter(pop_adatper);

        db.collection("Popular Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularModel popularModel = document.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                pop_adatper.notifyDataSetChanged();
                                //Toast.makeText(getActivity(), "Successfull.", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(getActivity(), "Error getting documents."+task.getException(), Toast.LENGTH_SHORT).show();
                        }}});

        //Home Category

        home_cat_rec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        homeCategoryList = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(),homeCategoryList);
        home_cat_rec.setAdapter(homeAdapter);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                homeCategoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();
                                //Toast.makeText(getActivity(), "Successfull.", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(getActivity(), "Error getting documents."+task.getException(), Toast.LENGTH_SHORT).show();
                        }}});

       return root;
    }

}