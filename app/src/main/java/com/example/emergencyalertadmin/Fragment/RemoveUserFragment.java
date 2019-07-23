package com.example.emergencyalertadmin.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emergencyalertadmin.Activities.Main2Activity;
import com.example.emergencyalertadmin.RemoveUserAdapter;
import com.example.emergencyalertadmin.Model.User;
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
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;


public class RemoveUserFragment extends Fragment {
    View view;
    FirebaseFirestore db;
    List<User> list;
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;
    RemoveUserAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_remove_user, container, false);
        db = FirebaseFirestore.getInstance();

        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        list =  new ArrayList<>();
        list();
        return view;
    }
    public void  fill_list()  {
        adapter = new RemoveUserAdapter(getContext(),list,this);
        recyclerView.setAdapter(adapter);
    }


    public void list(){
            db.collection("Users")
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
                                    list.add(new User(doc.getId(),doc.get("category").toString()));
                                    fill_list();
                            }
                        }
                    });
    }
    public void remove(final String username){
        DocumentReference docRef = db.collection("Users").document(username);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String category = document.get("category").toString();
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(category);
                        document.getReference().delete();
                    } else {

                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });
    }
   /* public void createDialog() {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this.getActivity().getApplicationContext());
        alertDlg.setMessage("Çıkış yapmak istediğinizden emin misiniz?");
        alertDlg.setCancelable(false);

        alertDlg.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        alertDlg.setNegativeButton("Iptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });
        alertDlg.create().show();
    }*/

}
