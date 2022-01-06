package com.ajmalyousufza.mygroceryshoppingcart.ui.home;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
import com.ajmalyousufza.mygroceryshoppingcart.adpters.RecommendedAdapter;
import com.ajmalyousufza.mygroceryshoppingcart.adpters.ViewAllAdapter;
import com.ajmalyousufza.mygroceryshoppingcart.databinding.FragmentHomeBinding;
import com.ajmalyousufza.mygroceryshoppingcart.models.HomeCategory;
import com.ajmalyousufza.mygroceryshoppingcart.models.PopularModel;
import com.ajmalyousufza.mygroceryshoppingcart.models.RecommendedModel;
import com.ajmalyousufza.mygroceryshoppingcart.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ScrollView scrollView;
    ProgressBar progressBar;

    RecyclerView pop_rec,home_cat_rec,recommended_recycler;
    FirebaseFirestore db;

    ///////////Search Box///////////
    EditText search_box;
    private List<ViewAllModel> viewAllModelList;
    private RecyclerView recyclerView_search;
    private ViewAllAdapter viewAllAdapter_search;

    //Popular item
    List<PopularModel> popularModelList;
    PopularAdatper pop_adatper;

    //Home explore Category
    List<HomeCategory> homeCategoryList;
    HomeAdapter homeAdapter;

    //Recommended category
    List<RecommendedModel> recommendedModelList;
    RecommendedAdapter recommendedAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_home,container,false);

       pop_rec = root.findViewById(R.id.pop_rec);
        home_cat_rec = root.findViewById(R.id.explore_rec);
        recommended_recycler = root.findViewById(R.id.recommented_rec);
        scrollView = root.findViewById(R.id.scroll_view_home);
        progressBar = root.findViewById(R.id.progressbar_home);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

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
                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
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

        //Recommended Products

        recommended_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recommendedModelList = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(getActivity(),recommendedModelList);
        recommended_recycler.setAdapter(recommendedAdapter);

        db.collection("Recommended Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommendedModel recommendedModel = document.toObject(RecommendedModel.class);
                                recommendedModelList.add(recommendedModel);
                                recommendedAdapter.notifyDataSetChanged();
                                //Toast.makeText(getActivity(), "Successfull.", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(getActivity(), "Error getting documents."+task.getException(), Toast.LENGTH_SHORT).show();
                        }}});

        search_box = root.findViewById(R.id.search_box);
        viewAllModelList = new ArrayList<>();
        viewAllAdapter_search = new ViewAllAdapter(getContext(),viewAllModelList);
        recyclerView_search = root.findViewById(R.id.search_recycleview);
        recyclerView_search.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_search.setAdapter(viewAllAdapter_search);
        recyclerView_search.setHasFixedSize(true);
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    viewAllModelList.clear();
                    viewAllAdapter_search.notifyDataSetChanged();
                }
                else{
                    searchProducto(editable.toString());
                }
            }
        });

       return root;
    }

    private void searchProducto(String type) {

        if(!type.isEmpty()){
            db.collection("Add Products").whereEqualTo("type",type)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful() && task.getResult()!=null){
                        viewAllModelList.clear();
                        viewAllAdapter_search.notifyDataSetChanged();

                        for(DocumentSnapshot doc : task.getResult().getDocuments()){
                            ViewAllModel viewAllModel = doc.toObject(ViewAllModel.class);
                            viewAllModelList.add(viewAllModel);
                            viewAllAdapter_search.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

    }

}