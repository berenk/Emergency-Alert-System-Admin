package com.example.emergencyalertadmin.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergencyalertadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UpdateUserFragment extends Fragment {

    View view;
    Spinner category;
    TextView username;
    List categoryList;
    ArrayAdapter adapter;
    FirebaseFirestore db;
    String categoryName;
    Button button;
    TextView belirtectext;
    String initialCategory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_update_user, container, false);
        username = view.findViewById(R.id.username);
        category = view.findViewById(R.id.spinner);
        button = view.findViewById(R.id.update);
        belirtectext = view.findViewById(R.id.belirtec);
        categoryList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        categoryName=  getArguments().getString("category");
        initialCategory = categoryName;
        username.setText(getArguments().getString("username"));
        list();
        belirtectext.setText("şuan "+ categoryName + " de bulunuyor.");
        button.setText("Değişiklik yok");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCategory(username.getText().toString());
            }
        });

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                 if(!initialCategory.equals(parent.getItemAtPosition(pos).toString())){
                     button.setText(parent.getItemAtPosition(pos).toString()+ " ile değiştir");
                 }
                 else {
                     button.setText("Değişiklik yok");
                 }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



         return view;
    }


    public void  fill_list()  {
        if(adapter == null){
            adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, categoryList);
            category.setAdapter(adapter);
        }else {
            //adapter update
            adapter.clear();
            adapter.addAll(categoryList);
            adapter.notifyDataSetChanged();
        }
    setSpinText(category,categoryName);
    }
    public void setSpinText(Spinner spin, String text)
    {
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            if(spin.getAdapter().getItem(i).toString().contains(text))
            {

                spin.setSelection(i);

            }
        }

    }

    //Veritabanında kategorilerin çekilmesi
    public void list(){
        db.collection("Categories")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("", "Listen failed.", e);
                            return;
                        }
                        if(value.isEmpty()){
                            return;
                        }
                        for (QueryDocumentSnapshot doc : value) {
                            categoryList.add(doc.getId());
                        }
                        fill_list();

                    }
                });
    }
    public void updateCategory(final String username){
        db.collection("Users").document(username).update(
                "category", category.getSelectedItem().toString()

        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(),"Güncelleme işlemi başarılı",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Güncelleme işlemi başarısız",Toast.LENGTH_LONG).show();
            }
        });
        initialCategory = category.getSelectedItem().toString();
        belirtectext.setText("şuan "+ category.getSelectedItem().toString() + " de bulunuyor.");
        button.setText("Değişiklik yok");

    }



}
