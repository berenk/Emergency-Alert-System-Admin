package com.example.emergencyalertadmin.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.emergencyalertadmin.Presenter.ILoginPresenter;
import com.example.emergencyalertadmin.Presenter.LoginPresenter;
import com.example.emergencyalertadmin.R;
import com.example.emergencyalertadmin.View.ILoginView;

public class MainActivity extends AppCompatActivity implements  ILoginView {
    EditText username, password;
    Button button;
    CheckBox rememberMeCheckbox ;
    SharedPreferences mPrefs;
    static final String PREFS_NAME = "PrefsFile";



    ILoginPresenter loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);








        loginPresenter = new LoginPresenter(this);
         Init();
         mPrefs=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
         getPreferencesData();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    loginPresenter.onLogin(username.getText().toString(),password.getText().toString());
                    if(rememberMeCheckbox.isChecked()){
                        Boolean boolisChecked = rememberMeCheckbox.isChecked();
                        SharedPreferences.Editor editor=mPrefs.edit();
                        editor.putString("pref_name",username.getText().toString());
                        editor.putString("pref_pass",password.getText().toString());
                        editor.putBoolean("pref_check",boolisChecked);
                        editor.apply();
                        Toast.makeText(getApplicationContext(),"Kullanıcı bilgileri kaydedildi", Toast.LENGTH_SHORT).show();

                    }else{
                        System.out.println("checkbox tikli degil");
                    }
             }
        });

    }

    private void getPreferencesData(){
        SharedPreferences sp = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if(sp.contains("pref_name")){
            String u = sp.getString("pref_name","not found.");
            username.setText(u);
        }
        if(sp.contains("pref_pass")){
            String p = sp.getString("pref_pass","not found.");
            password.setText(p);

        }
        if(sp.contains("pref_bool")){
            Boolean b = sp.getBoolean("pref_check",false);
            rememberMeCheckbox.setChecked(b);
        }

    }


    void Init(){
        username = findViewById(R.id.txt_username);
        password = findViewById(R.id.txt_password);
        button = findViewById(R.id.btn_login);
        rememberMeCheckbox = findViewById(R.id.remember);

    }

    @Override
    public void onLoginResult(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
