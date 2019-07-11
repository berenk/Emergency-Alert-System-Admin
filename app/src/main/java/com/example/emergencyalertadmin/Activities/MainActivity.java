package com.example.emergencyalertadmin.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.emergencyalertadmin.Presenter.ILoginPresenter;
import com.example.emergencyalertadmin.Presenter.LoginPresenter;
import com.example.emergencyalertadmin.R;
import com.example.emergencyalertadmin.View.ILoginView;

public class MainActivity extends AppCompatActivity implements  ILoginView {
    EditText username, password;
    Button button;
     ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
        loginPresenter = new LoginPresenter(this);
         Init();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    loginPresenter.onLogin(username.getText().toString(),password.getText().toString());
             }
        });
    }

    void Init(){
        username = findViewById(R.id.txt_username);
        password = findViewById(R.id.txt_password);
        button = findViewById(R.id.btn_login);
        username.setText("admin@admin.com");
        password.setText("admin123");
    }

    @Override
    public void onLoginResult(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
