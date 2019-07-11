package com.example.emergencyalertadmin.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emergencyalertadmin.CategoryAdapter;
import com.example.emergencyalertadmin.Model.Category;
 import com.example.emergencyalertadmin.R;
 import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RemoveCategoryFragment extends Fragment {

    View view;
    FirebaseFirestore db;
    List<Category> list;
    RecyclerView recyclerView;
    SparseBooleanArray mCheckStates ;

    RecyclerView.LayoutManager layoutManager;
    CategoryAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_remove_category, container, false);
        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.category_recyclerView);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        list =  new ArrayList<>();
        list();
        return view;
    }
    public void remove(final String category){
        DocumentReference docRef = db.collection("Categories").document(category);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        document.getReference().delete();
                    } else {

                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });
    }
    public void  fill_list()  {
        adapter = new CategoryAdapter(getContext(),list,this);
        recyclerView.setAdapter(adapter);
    }

    public void list(){
        db.collection("Categories")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        list.clear();
                        if (e != null) {
                            Log.w("", "Listen failed.", e);
                            return;
                        }
                        if(value.isEmpty()){
                            fill_list();
                        }
                        for (QueryDocumentSnapshot doc : value) {
                            list.add(new Category(doc.getId(),""));
                            fill_list();
                        }
                    }
                });
    }



}
