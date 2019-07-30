package com.example.emergencyalertadmin.Presenter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.emergencyalertadmin.Activities.Main2Activity;
import com.example.emergencyalertadmin.View.ILoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter implements ILoginPresenter {

    ILoginView loginView;
    private FirebaseAuth mAuth;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void onLogin(String username,String password) {
        if(username.isEmpty() || password.isEmpty()){
            loginView.onLoginResult("Kullanıcı adı veya sifre boş bırakılamaz");
        }else {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent i = new Intent((Context) loginView, Main2Activity.class);
                        loginView.onLoginResult("Giriş Yapıldı");
                        ((Activity)loginView).finish();
                        ((Context) loginView).startActivity(i);
                    } else {
                        loginView.onLoginResult("Giriş Yapılamadı");
                    }
                }
            });
        }

    }
}
