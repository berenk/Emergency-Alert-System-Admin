package com.example.emergencyalertadmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergencyalertadmin.Fragment.RemoveUserFragment;
import com.example.emergencyalertadmin.Fragment.UpdateUserFragment;
import com.example.emergencyalertadmin.Model.User;

import java.util.List;


public class RemoveUserAdapter extends RecyclerView.Adapter<RemoveUserAdapter.MyViewHolder> {


    Context context;
    List<User> list;
    RemoveUserFragment fragment;

    public RemoveUserAdapter(Context context, List<User> list, RemoveUserFragment fragment) {
        this.context = context;
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.remove_user_layout, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.username.setText(list.get(i).getEmail());
        myViewHolder.category.setText(list.get(i).getCategory());
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeFragment changeFragment = new ChangeFragment(context);
                UpdateUserFragment fragmentb = new UpdateUserFragment();
                Bundle args = new Bundle();
                args.putString("category", list.get(i).getCategory());
                args.putString("username", list.get(i).getEmail());
                fragmentb.setArguments(args);
                changeFragment.change(fragmentb);
            }
        });
        myViewHolder.remove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                alertDialog.setMessage("Kullanıcıyı silmek istediğinizden emin misiniz?");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fragment.remove(myViewHolder.username.getText().toString());


                    }
                });
                alertDialog.setNegativeButton("Iptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.create().show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        Button remove;
        TextView category;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usr_name);
            remove = itemView.findViewById(R.id.removeUser);
            category = itemView.findViewById(R.id.category);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}


