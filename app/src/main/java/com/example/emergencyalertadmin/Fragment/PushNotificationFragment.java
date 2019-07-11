package com.example.emergencyalertadmin.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.emergencyalertadmin.Activities.MainActivity;
import com.example.emergencyalertadmin.NotificationModel;
import com.example.emergencyalertadmin.R;
import com.example.emergencyalertadmin.UtilsRetrofit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PushNotificationFragment extends Fragment {

    View view;
    EditText title, body;
    Spinner spinner;
    FloatingActionButton buttonSend;
    FirebaseFirestore db;
    String username;
    FirebaseAuth auth;
    FirebaseUser user;
    List categoryList;
    ArrayAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_push_notification, container, false);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        categoryList = new ArrayList<>();

        if (user == null) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            view.getContext().startActivity(intent);
            return view;
        }
        username = user.getEmail();
        Init();
        list();
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    NotificationModel notificationModel = new NotificationModel(title.getText().toString(), body.getText().toString(), spinner.getSelectedItem().toString());
                    UtilsRetrofit.getOurInstance().sendNotification(notificationModel, getActivity());
                    insertData(title.getText().toString(), body.getText().toString(), spinner.getSelectedItem().toString());
                }
            }
        });
        return view;
    }

    public void Init() {
        title = view.findViewById(R.id.notification_title);
        body = view.findViewById(R.id.notification_body);
        buttonSend = view.findViewById(R.id.btn_send);
        spinner = view.findViewById(R.id.notificationSpinner);
    }

    public boolean validate() {
        if (title.getText().toString().isEmpty() || body.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Alanlar boş bırakılamaz", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public void insertData(String title, String body, String selectedCategory) {
        // Create a new user with a first and last name
        Map<String, Object> notif = new HashMap<>();
        notif.put("title", title);
        notif.put("body", body);
        notif.put(selectedCategory, true);

// Add a new document with a generated ID
        db.collection("Notifications")
                .add(notif)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("", "Error adding document", e);
                    }
                });
    }

    public void fill_list() {
        if (adapter == null) {
            adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, categoryList);
            spinner.setAdapter(adapter);
        } else {
            //adapter update
            adapter.clear();
            adapter.addAll(categoryList);
            adapter.notifyDataSetChanged();
        }

    }

    //Veritabanında kategorilerin çekilmesi
    public void list() {
        db.collection("Categories")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("", "Listen failed.", e);
                            return;
                        }
                        if (value.isEmpty()) {
                            return;
                        }
                        for (QueryDocumentSnapshot doc : value) {
                            categoryList.add(doc.getId());
                        }
                        fill_list();

                    }
                });
    }


}

