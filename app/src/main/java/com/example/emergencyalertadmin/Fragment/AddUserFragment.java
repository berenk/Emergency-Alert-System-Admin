package com.example.emergencyalertadmin.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.emergencyalertadmin.NotificationModel;
import com.example.emergencyalertadmin.R;
import com.example.emergencyalertadmin.UtilsRetrofit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddUserFragment extends Fragment {
    EditText username;
    FloatingActionButton add_user;
    View view;
    Spinner spinner;
    FirebaseFirestore db;
    List categoryList;
    ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_user, container, false);
        Init();
        db = FirebaseFirestore.getInstance();
        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(v.getContext());
                alertDlg.setTitle("Bu kullanıcıyı eklemek istediğinizden emin misiniz ?");

                alertDlg.setMessage("Kullanıcı adı : " + username.getText().toString() + "\n\n" +
                        "Kategori : " + spinner.getSelectedItem().toString());
                alertDlg.setCancelable(false);

                alertDlg.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        searchUser(username.getText().toString());
                        username.getText().clear();
                    }
                });
                alertDlg.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                    }
                });
                alertDlg.create().show();



            }
        });
        list();

        return view;
    }

    public void Init() {
        username = view.findViewById(R.id.username);
        add_user = view.findViewById(R.id.add_user);
        spinner = view.findViewById(R.id.categorySpinner);
        categoryList = new ArrayList<>();

    }

    public void addUser(String username, String category) {
        Map<String, Object> user = new HashMap<>();
        user.put("category", category);

        db.collection("Users").document(username).set(user)
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

    public void searchUser(final String username){
        DocumentReference docRef = db.collection("Users").document(username);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(getActivity(), "Böyle bir kayıt mevcut", Toast.LENGTH_LONG).show();

                    } else {
                        addUser(username, spinner.getSelectedItem().toString());
                        Toast.makeText(getActivity(), "Oluşturuldu", Toast.LENGTH_LONG).show();


                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });
    }

    public void  fill_list()  {
        if(adapter == null){
            adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, categoryList);
            spinner.setAdapter(adapter);
        }else {
            //adapter update
              adapter.clear();
              adapter.addAll(categoryList);
              adapter.notifyDataSetChanged();
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


}
