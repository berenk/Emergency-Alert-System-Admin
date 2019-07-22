package com.example.emergencyalertadmin.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emergencyalertadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddCategoryFragment extends Fragment {
    View view;
    EditText category_name, category_description;
    FloatingActionButton add_category;
    FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_category, container, false);
        category_name = view.findViewById(R.id.category_name);
        add_category = view.findViewById(R.id.add_category);
        category_description = view.findViewById(R.id.category_description);
        db = FirebaseFirestore.getInstance();
        add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCategory(category_name.getText().toString(),category_description.getText().toString());
                System.out.println();
                category_description.getText().clear();
                category_name.getText().clear();
            }
        });
        return view;
    }

    public void addCategory(String category, String description) {
        Map<String, Object> user = new HashMap<>();
        user.put("description", description);
        //user.put("category", category);

        db.collection("Categories").document(category).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void searchCategory(final String category, final String description){
        DocumentReference docRef = db.collection("Categories").document(category);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(getActivity(), "Böyle bir kayıt mevcut", Toast.LENGTH_LONG).show();

                    } else {
                        addCategory(category, description);
                        Toast.makeText(getActivity(), "Oluşturuldu", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });
    }
}
